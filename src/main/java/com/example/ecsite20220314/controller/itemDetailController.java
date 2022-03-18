package com.example.ecsite20220314.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.ecsite20220314.domain.Item;
import com.example.ecsite20220314.domain.OrderTopping;
import com.example.ecsite20220314.domain.Topping;
import com.example.ecsite20220314.form.OrderItemForm;
import com.example.ecsite20220314.service.itemDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itemDetail")
public class itemDetailController {
    @Autowired
    private itemDetailService   service;

    @Autowired
    private HttpSession session;

    @ModelAttribute
    public  OrderItemForm   setUpForm(){
        return  new OrderItemForm();
    }

    @RequestMapping("")
    public String  index(int id,Model  model){
        Item    itemObject=service.load(id);
        List<Topping> toppings=service.findAll();
        model.addAttribute("itemObject", itemObject);
        model.addAttribute("toppings", toppings);
        return  "item_detail";
    }
    @RequestMapping("/insert")
    public  String  insert(OrderItemForm    form){
        //orderIDを取得・なければテーブル作成
        Integer nowId=(Integer) session.getAttribute("userId");
        if(nowId==null){
            nowId=(Integer) session.getAttribute("preId");
        }
        if(nowId==null){
            nowId=Math.abs(UUID.randomUUID().hashCode());
            session.setAttribute("preId", nowId);
        }
        //userId or preId格納
        int orderId=service.searchId(nowId.intValue());


        if(session.getAttribute("userId")!=null){
            //両方あればpreIdを切ってもらう
            if(session.getAttribute("preId") !=null){
                return  "/login";
            }
            //ログイン状態での処理
            else if(session.getAttribute("preId") ==null){
                cartAdd(form,  orderId); 
            }
        }else{
            cartAdd(form,  orderId);
        }
        return  "redirect:/shoppingCart";
    }

    //カート追加機能
    private  String cartAdd(OrderItemForm    form,int orderId){
        form.setOrderId(orderId);
        int orderItemId=service.insertAndId(form).intValue();
        //トッピング追加
        if(form.getOrderToppingList().size()!=0){
            for (OrderTopping toppings : form.getOrderToppingList()) {
                toppings.setOrderItemId(orderItemId);
                service.insertTopping(toppings);
            }
        }
        return  "redirect:/shoppingCart";

    }
    
}
