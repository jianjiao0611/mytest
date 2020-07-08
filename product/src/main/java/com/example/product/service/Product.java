package com.example.product.service;

import org.springframework.stereotype.Service;

@Service
public class Product extends BaseService {

    @Override
    public void print() {
        System.out.println("product");
    }

    public void porint() {
        System.out.println("sss");
    }
}
