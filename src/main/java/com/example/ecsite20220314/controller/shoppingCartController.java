package com.example.ecsite20220314.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shppingCart")
public class shoppingCartController {
    @RequestMapping("")
    public String index(){
        return "cart_list";
    }
}
