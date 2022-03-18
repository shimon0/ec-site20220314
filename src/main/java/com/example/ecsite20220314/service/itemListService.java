package com.example.ecsite20220314.service;

import java.util.List;

import com.example.ecsite20220314.domain.Item;
import com.example.ecsite20220314.repository.itemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class itemListService {
    @Autowired
    private itemRepository  repository;

    public  List<Item>  findAll(){
        return  repository.findAll();
    }

    public  List<Item>  findAllDesc(){
        return  repository.findAllDesc();
    }

    public  List<Item>  search(String   word){
        return  repository.search(word);
    }

    public  List<Item>  searchDesc(String   word){
        return  repository.searchDesc(word);
    }
}
