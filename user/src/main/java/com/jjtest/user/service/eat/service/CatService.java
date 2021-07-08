package com.jjtest.user.service.eat.service;

import com.jjtest.user.service.eat.AnimalAnnotation;
import com.jjtest.user.service.eat.AnimalEnum;
import org.springframework.stereotype.Service;

@Service
@AnimalAnnotation(AnimalEnum.CAT)
public class CatService implements IEatService {

    @Override
    public void eat() {
        System.out.println("鱼");
    }
}
