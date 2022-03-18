package com.example.ecsite20220314.repository;

import com.example.ecsite20220314.domain.OrderTopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class orderToppingRepository {
    @Autowired
    private NamedParameterJdbcTemplate  template;

    private static  final   RowMapper<OrderTopping> OT_ROW_MAPPER=(rs,i)->{
        OrderTopping    orderTopping=new OrderTopping();
        orderTopping.setId(rs.getInt("id"));
        orderTopping.setOrderItemId(rs.getInt("order_item_id"));
        orderTopping.setToppingId(rs.getInt("topping_id"));
        return orderTopping;
    };
    public  void insertTopping(OrderTopping orderTopping){
        String  sql="INSERT into order_toppings(order_item_id,topping_id) VALUES(:orderItemId,:toppingId);";
        SqlParameterSource  param=new   MapSqlParameterSource().addValue("orderItemId", orderTopping.getOrderItemId()).addValue("toppingId",orderTopping.getToppingId());
        template.update(sql, param);
    }
}
