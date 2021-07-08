package com.jjtest.user.service.eat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimalEnum {

    CAT(1,"猫"),
    DOG(2,"狗");

    private int type;

    private String name;


}
