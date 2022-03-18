package com.example.ecsite20220314.repository;

import java.util.List;


import com.example.ecsite20220314.domain.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
@Repository
public class itemRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static  final   RowMapper<Item> ITEM_ROW_MAPPER=(rs,i) ->{
        Item    item    =   new    Item();
        item.setId(rs.getInt("id"));
        item.setImagePath(rs.getString("image_path"));
        item.setName(rs.getString("name"));
        item.setPriceM(rs.getInt("price_m"));
        item.setPriceL(rs.getInt("price_l"));
        item.setDescription(rs.getString("description"));

        return item;
    };
    /**
     * 全件検索（昇順）
     * @return 商品一覧
     */
    public  List<Item>  findAll(){
        String  sql="SELECT id,name,image_path,price_m,price_l,description FROM items ORDER BY price_m ASC";
        List<Item>  items=template.query(sql, ITEM_ROW_MAPPER);
        return items;
    }

    /**
     * 全件検索（降順）
     * @return 商品一覧
     */
    public  List<Item>  findAllDesc(){
        String  sql="SELECT id,name,image_path,price_m,price_l,description FROM items ORDER BY price_m DESC";
        List<Item>  items=template.query(sql, ITEM_ROW_MAPPER);
        return items;
    }
    /**
     * 曖昧検索（昇順）
     * @return  商品一覧
     *   
     * */
    public  List<Item>  search(String   word){
        String  sql="SELECT id,name,image_path,price_m,price_l,description FROM items WHERE name like %:name% ORDER BY price_m ASC;";
        SqlParameterSource param=new    MapSqlParameterSource().addValue("name", word);
        List<Item>  items=template.query(sql, param, ITEM_ROW_MAPPER);
        return  items;
    }

    /**
     * 曖昧検索（降順）
     * @return  商品一覧
     *   
     * */
    public  List<Item>  searchDesc(String   word){
        String  sql="SELECT id,name,image_path,price_m,price_l,description FROM items WHERE name like %:name% ORDER BY price_m DESC;";
        SqlParameterSource param=new    MapSqlParameterSource().addValue("name", word);
        List<Item>  items=template.query(sql, param, ITEM_ROW_MAPPER);
        return  items;
    }

    /**
     * 
     * 商品詳細画面を表示
     * @return 商品詳細
     */

     public Item load(int id){
         String sql="SELECT id,name,image_path,price_m,price_l,description FROM items WHERE id=:id";
         SqlParameterSource param=new   MapSqlParameterSource().addValue("id", id);
         Item items=template.queryForObject(sql, param, ITEM_ROW_MAPPER);
         return items;
    }    
}
