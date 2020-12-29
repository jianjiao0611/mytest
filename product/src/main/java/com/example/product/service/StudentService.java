package com.example.product.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StudentService extends BaseService {

    @Transactional
    @Override
    public void print() {
        System.out.println("student");
        log.info("ssss");
    }
}
