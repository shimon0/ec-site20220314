package com.example.ecsite20220314.repository;


import java.util.List;
import java.util.Map;

import com.example.ecsite20220314.domain.User;
import com.example.ecsite20220314.form.LoginForm;
import com.example.ecsite20220314.form.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class userRepository {
    @Autowired
    private NamedParameterJdbcTemplate  template;

    private final static RowMapper<User>USER_ROW_MAPPER=(rs,i)->{
        User    user=new User();
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setTelephone(rs.getString("telephone"));
        user.setZipcode(rs.getString("zipcode"));

        return user;
    };

    /**
     * ログインパスワード確認
     * @param userForm
     * @return
     */
    public  User login(LoginForm  loginForm){
        String  sql="SELECT * FROM users WHERE email=:email";
        SqlParameterSource param=new MapSqlParameterSource().addValue("email", loginForm.getEmail());
        User u=template.queryForObject(sql, param, USER_ROW_MAPPER);
        
        return  u;
    }

    /**
     * メールアドレス確認機能
     * @param userForm
     * @return
     */
    public  int  confirmMail(UserForm  userForm){
        String  sql="SELECT email FROM users WHERE email=:email";
        SqlParameterSource param=new MapSqlParameterSource().addValue("email", userForm.getEmail());
        List<Map<String, Object>>  uList=template.queryForList(sql, param);
        if (uList.isEmpty()) {
            return  0;
        }
        return  uList.size();
    }

    public void register(UserForm userForm){
        String  sql="INSERT INTO users(name, email, password, zipcode, address, telephone)"
        +" VALUES(:name, :email, :password, :zipcode, :address, :telephone);";

        SqlParameterSource param=new MapSqlParameterSource()
        .addValue("name",userForm.getName()).addValue("email",userForm.getEmail()).addValue("password",userForm.getPassword())
        .addValue("zipcode",userForm.getZipcode()).addValue("address",userForm.getAddress()).addValue("telephone",userForm.getTelephone());
        template.update(sql, param);
    }

}
