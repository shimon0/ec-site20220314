package com.example.ecsite20220314.service;

import com.example.ecsite20220314.domain.Order;
import com.example.ecsite20220314.repository.orderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class shoppingCartService {
    @Autowired
    private orderRepository orderRepository;

    public  Order   cartInfo(int id){
        return  orderRepository.cartInfo(id);
    }
}
