package com.example.product.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService extends BaseService {

    @Override
    public void print() {
        System.out.println("student");
        log.info("ssss");
    }
}
