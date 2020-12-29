package com.jjtest.taskcenter.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPO {
    private Long id;

    private String productName;

    private BigDecimal price;
}
