package com.example.ecsite20220314.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.ecsite20220314.domain.OrderItem;
import com.example.ecsite20220314.service.shoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shppingCart")
public class shoppingCartController {

    @Autowired
    private shoppingCartService service;

    @Autowired
    private HttpSession session;
    
    @RequestMapping("")
    public String index(Model  model){
        if (session.getAttribute("userId")==null) {
            return  "/login";
        }
        List<OrderItem> orderItems=service.cartInfo((int)session.getAttribute("userId"));
        model.addAttribute("orderItems", orderItems);
        HashMap<Integer,Integer>totalMap = new HashMap<>();

        for(OrderItem order:orderItems){
            totalMap.put(order.getId(),order.getSubTotal());
        }
        model.addAttribute("totalMap", totalMap);
        return "cart_list";
    }
}
