package com.example.ecsite20220314.service;

import java.util.List;

import com.example.ecsite20220314.domain.OrderItem;
import com.example.ecsite20220314.repository.orderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class shoppingCartService {
    @Autowired
    private orderRepository orderRepository;

    public  List<OrderItem>   cartInfo(int id){
        return  orderRepository.cartInfo(id);
    }
}
