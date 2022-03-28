package com.example.ecsite20220314.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class logoutController {
    
    @Autowired
    private HttpSession session;

    @RequestMapping("")
    public  String  index(){
        session.invalidate();
        return  "redirect:/login";
    }
}
