package com.example.ecsite20220314.repository;

import com.example.ecsite20220314.domain.OrderItem;
import com.example.ecsite20220314.form.OrderItemForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class orderItemRepository {
    @Autowired
    private NamedParameterJdbcTemplate  template;

    private static  final   RowMapper<OrderItem>    ORDERITEM_ROW_MAPPER=(rs,i)->{
        OrderItem   orderItem   =new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setItemId(rs.getInt("item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        char[] c=rs.getString("size").toCharArray();
        orderItem.setSize(c[0]);

        return orderItem;
    };

    private static final RowMapper<OrderItem>   O_ROW_MAPPER=(rs,i)->{
        OrderItem   orderItem=new OrderItem();
        orderItem.setId(rs.getInt("id"));
        return orderItem;
    };

    public  Integer  insertAndId(OrderItemForm    form){
        String  sql="INSERT INTO order_items(item_id,order_id,quantity,size VALUES(:itemId,:orderId,:quantity,:size) RETURNING id;";
        SqlParameterSource  param=new   MapSqlParameterSource().addValue("itemId", form.getItemId())
                                        .addValue("orderId", form.getOrderId()).addValue("quantity", form.getQuantity()).addValue("size", form.getSize());
        OrderItem   oItemId=template.queryForObject(sql, param, O_ROW_MAPPER);
        return  oItemId.getId();
    }
}
