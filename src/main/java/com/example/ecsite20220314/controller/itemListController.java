package com.example.ecsite20220314.controller;

import java.util.List;

import com.example.ecsite20220314.domain.Item;
import com.example.ecsite20220314.service.itemListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itemList")
public class itemListController {
    @Autowired
    private itemListService service;

    @RequestMapping("")
    public  String  index(Model model){
        List<Item>  itemList=service.findAll();
        model.addAttribute("itemList", itemList);
        return  "itemList";
    }
}
