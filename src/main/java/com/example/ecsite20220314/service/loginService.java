package com.example.ecsite20220314.service;

import com.example.ecsite20220314.domain.User;
import com.example.ecsite20220314.form.LoginForm;
import com.example.ecsite20220314.form.UserForm;
import com.example.ecsite20220314.repository.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class loginService {
    @Autowired
    private userRepository  repository;

    public  User    login(LoginForm  loginForm){
        return repository.login(loginForm);
    }

    public  void    register(UserForm   userForm){
        repository.register(userForm);
    }

    public int confirmMail(UserForm userForm){
        return  repository.confirmMail(userForm);
    } 
}
