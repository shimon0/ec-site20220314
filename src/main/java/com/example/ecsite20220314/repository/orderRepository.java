package com.example.ecsite20220314.repository;

import com.example.ecsite20220314.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
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
        order.setUserId(rs.getInt("user_id"));
        return  order;
    };

    /**
     * orderテーブル確認sql
     * Idなければインサート
     * @param userId
     * @return
     */
    public  int searchId(int id){
        String  sql="SELECT id FROM orders WHERE user_id=:userId    AND status=0";
        SqlParameterSource  param=new   MapSqlParameterSource().addValue("userId", id);
        Order order=template.queryForObject(sql, param, ORDER_ROW_MAPPER);
        if(order.getId()==null){
            order=insertOrder(id);
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
        String  insertSql="INSERT INTO orders(user_id,status,total_price) VALUES(:userId,0,0);";
            SqlParameterSource param2=new   MapSqlParameterSource().addValue("userId", id);
            Order order=template.queryForObject(insertSql, param2, ORDER_ROW_MAPPER);
            return  order;
    }

    /**
     * ショッピングカート表示
     * @param id
     * @return
     */
    public  Order   cartInfo(int    id){
        String  sql="SELECT item.image_path,item.name,ord_item.size,ord_item.quantity,item.price_m,item.price_l,topping.name,topping.price_m,topping.price_l FROM orders AS ord "
        +" JOIN order_items AS ord_item ON ord.id=ord_item.order_id "
        +" LEFT OUTER JOIN items AS item ON ord_item.id=item.id"
        +" LEFT OUTER JOIN order_toppings AS ord_topping ON ord_item.id=ord_topping.order_item_id"
        +" LEFT OUTER JOIN toppings AS topping ON ord_topping.topping_id=topping.id"
        +" WHERE ord.user_id=:userId AND ord.status=0";
        SqlParameterSource param2=new   MapSqlParameterSource().addValue("userId", id);
        Order   order=template.query(sql, param2, rch);
        return  order;

    }
}


