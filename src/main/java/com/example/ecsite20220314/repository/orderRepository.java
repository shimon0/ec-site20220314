package com.example.ecsite20220314.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ecsite20220314.domain.Item;
import com.example.ecsite20220314.domain.Order;
import com.example.ecsite20220314.domain.OrderItem;
import com.example.ecsite20220314.domain.OrderTopping;
import com.example.ecsite20220314.domain.Topping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class orderRepository {
    @Autowired
    private NamedParameterJdbcTemplate  template;

    /**
     * orderテーブル検索＆インサート用RowMapper
     * 
     */
    private static  final   RowMapper<Order>    ORDER_ROW_MAPPER=(rs,i)->{
        Order   order=new Order();
        order.setId(rs.getInt("id"));
        return  order;
    };

    /**
     * ショッピングカート表示
     * 
     */
    private static  final   ResultSetExtractor<List<OrderItem>> O_RESULT_SET_EXTRACTOR=(rs)->{
        List<OrderItem> orderItems=new ArrayList<>();
        List<OrderTopping> orderToppingList=null;
        
        int beforeIdNum=0;

        while (rs.next()) {
            int nowIdNum=rs.getInt("ord_id");
        
            if (nowIdNum !=beforeIdNum) {
                OrderItem   orderItem=new OrderItem();

                orderItem.setOrderId(rs.getInt("ord_id"));
                orderItem.setItemId(rs.getInt("item_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                char[] size=rs.getString("size").toCharArray();
                orderItem.setSize(size[0]);

                Item item=new Item();
                item.setName(rs.getString("item_name"));
                item.setImagePath(rs.getString("image_path"));
                item.setPriceM(rs.getInt("price_m"));
                item.setPriceL(rs.getInt("price_l"));
                
                orderItem.setItem(item);

                orderToppingList=new ArrayList<OrderTopping>();
                orderItem.setOrderToppingList(orderToppingList);
                orderItems.add(orderItem);
                
            }
            if (rs.getInt("topping_id")!=0) {
                OrderTopping    orderTopping=new OrderTopping();
                Topping topping=new Topping();
                topping.setId(rs.getInt("topping_id"));
                topping.setName(rs.getString("topping_name"));
                topping.setPriceM(rs.getInt("topping_price_m"));
                topping.setPriceL(rs.getInt("topping_price_l"));
                orderTopping.setTopping(topping);
                orderTopping.setOrderItemId(rs.getInt("order_item_id"));
                orderToppingList.add(orderTopping);
 
            }
            beforeIdNum=nowIdNum;
        }

        return orderItems;
    };



    /**
     * orderテーブル確認sql
     * Idなければインサート
     * @param userId
     * @return
     */
    public  int searchId(int nowId){
        String  sql="SELECT id FROM orders WHERE user_id=:userId AND status=0";
        SqlParameterSource  param=new MapSqlParameterSource().addValue("userId", nowId);
        Order order=new Order();
        List<Order> orderList=template.query(sql, param,ORDER_ROW_MAPPER);
        if (orderList.size()!=0) {
            order.setId(orderList.get(0).getId());
        }else{
            order=insertOrder(nowId); 
        }
        return  order.getId();
    }

    /**
     * Insert
     * orderテーブル
     * @param id
     * @return
     */
    public  Order insertOrder(int id){
        String  insertSql="INSERT INTO orders(user_id,status,total_price) VALUES(:userId,0,0) RETURNING id;";
            SqlParameterSource param2=new   MapSqlParameterSource().addValue("userId",id);
            Order order=template.queryForObject(insertSql, param2, ORDER_ROW_MAPPER);
            return  order;
    }

    /**
     * ショッピングカート表示
     * @param id
     * @return
     */
    public  List<OrderItem> cartInfo(int id){
        String  sql=
        "SELECT ord_item.id ord_id,item.id item_id,item.image_path,item.name item_name,ord_item.size,ord_item.quantity,item.price_m,item.price_l,"
        +" ord_topping.order_item_id,topping.id topping_id,topping.name topping_name,topping.price_m topping_price_m,topping.price_l topping_price_l FROM orders AS ord "
        +" JOIN order_items AS ord_item ON ord.id=ord_item.order_id "
        +" LEFT OUTER JOIN items AS item ON ord_item.item_id=item.id"
        +" LEFT OUTER JOIN order_toppings AS ord_topping ON ord_item.id=ord_topping.order_item_id"
        +" LEFT OUTER JOIN toppings AS topping ON ord_topping.topping_id=topping.id"
        +" WHERE ord.user_id=:userId AND ord.status=0";
        SqlParameterSource param2=new   MapSqlParameterSource().addValue("userId", id);
        List<OrderItem>   order=template.query(sql, param2, O_RESULT_SET_EXTRACTOR);
        return  order;

    }

    /**
     * ショッピングカートの内容更新
     * ゲスト→ログイン状態
     * @param userId
     * @param preId
     */
    public void updateGeustCart(int userId,int preId){
        String sql="UPDATE orders SET user_id=:userId WHERE user_id=:preId";
        SqlParameterSource idUpdateSql=new MapSqlParameterSource().addValue("userId", userId).addValue("preId",preId);
        template.update(sql,idUpdateSql);     
    }
}


