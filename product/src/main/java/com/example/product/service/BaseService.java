package com.example.product.service;

public abstract class BaseService {

    public void send() {
        this.print();
    }

    public abstract void print();

}
