package com.example.product.service;

import org.springframework.stereotype.Service;

@Service
public class Test2 implements TestBaseService {
    @Override
    public void a() {
        System.out.println("222222");
    }
}
