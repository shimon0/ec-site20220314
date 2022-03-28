package com.example.ecsite20220314.service;

import java.util.List;

import com.example.ecsite20220314.domain.Item;
import com.example.ecsite20220314.domain.OrderTopping;
import com.example.ecsite20220314.domain.Topping;
import com.example.ecsite20220314.form.OrderItemForm;
import com.example.ecsite20220314.repository.itemRepository;
import com.example.ecsite20220314.repository.orderItemRepository;
import com.example.ecsite20220314.repository.orderRepository;
import com.example.ecsite20220314.repository.orderToppingRepository;
import com.example.ecsite20220314.repository.toppingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class itemDetailService {
    @Autowired
    private itemRepository  itemRepository;

    @Autowired
    private toppingRepository toppingRepository;

    @Autowired
    private orderItemRepository orderItemRepository;

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private orderToppingRepository  orderToppingRepository;


    public  Item   load(int id){
        return itemRepository.load(id);
    }

    public  List<Topping> findAll(){
        return  toppingRepository.findAll();
    }

    public  Integer insertAndId(OrderItemForm   form){
        return  orderItemRepository.insertAndId(form);
    }

    public  int searchId(int  id){
        return  orderRepository.searchId(id);
    }
    public  void    insertTopping(OrderTopping  orderTopping){
        orderToppingRepository.insertTopping(orderTopping);
    }

    
}
