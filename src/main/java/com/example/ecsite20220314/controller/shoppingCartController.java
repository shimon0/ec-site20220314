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
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @Autowired
    private shoppingCartService service;

    @Autowired
    private HttpSession session;
    
    @RequestMapping("")
    public String index(Model  model){
        HashMap<Integer,Integer>totalMap = new HashMap<>();

        //ログイン状態での表示処理
        if (session.getAttribute("userId")!=null) {
            List<OrderItem> orderItems=service.cartInfo((Integer)session.getAttribute("userId"));
            model.addAttribute("orderItems", orderItems);

            for(OrderItem order:orderItems){
            totalMap.put(order.getId(),order.getSubTotal());
            }
        //ゲスト状態での表示処理            
        }else if(session.getAttribute("preId")!=null){
            List<OrderItem> orderItems=service.cartInfo((Integer)session.getAttribute("preId"));
            model.addAttribute("orderItems", orderItems);

            for(OrderItem order:orderItems){
            totalMap.put(order.getId(),order.getSubTotal());
            }
        //例外
        }else{
            return  "redirect:/login";
        }
        model.addAttribute("totalMap", totalMap);

        
        return "cart_list";
    }
}
