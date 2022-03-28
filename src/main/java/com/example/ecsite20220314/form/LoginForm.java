package com.example.ecsite20220314.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginForm {
    
    @Email
    @Size(min=1,max = 127,message = "1文字以上127文字以内で記載してください")
    private String  email;
    @Size(min=1,max = 127,message = "1文字以上127文字以内で記載してください")
    private String  password;


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
