package com.example.product.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProductVo {

    private Integer id;

    private String name;

    private List<ProductVo> child;

}
