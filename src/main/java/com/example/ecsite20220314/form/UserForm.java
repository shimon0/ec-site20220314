package com.example.ecsite20220314.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserForm {
    private Integer id;
    @NotBlank(message="名前は必須です")
    private String  name;
    @Email(message="メールアドレスの形式が不正です")
    private String  email;
    @Size(min=1,max = 127,message = "パスワードは1文字以上127文字以内で記載してください")
    private String  password;
    private String  confirmPassword;
    @NotBlank(message="必須です")
    private String  zipcode;
    @NotBlank(message="必須です")
    private String  address;
    @NotBlank(message="必須です")
    private String  telephone;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
}
