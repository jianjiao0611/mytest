package com.jjtest.user.service.eat.factory;

import com.jjtest.tool.util.KeyWordLuceneService;
import com.jjtest.tool.util.SpringUtils;
import com.jjtest.user.service.eat.AnimalAnnotation;
import com.jjtest.user.service.eat.AnimalEnum;
import com.jjtest.user.service.eat.service.IEatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 动物工厂类
 */
@Component
public class AnimalFactory {

    private static Map<Integer, IEatService> map = new HashMap<>();

    @Autowired
    private SpringUtils springUtils;
    @Autowired
    private KeyWordLuceneService keyWordLuceneService;

//    public static AnimalFactory animalFactory;

    public AnimalFactory() {
        System.out.println(11111);
    }

    @PostConstruct
    public void init() {
//        animalFactory = this;
        Map<String, Object> beansWithAnnotation = springUtils.getBeanFactory().getBeansWithAnnotation(AnimalAnnotation.class);
        for(Object o :beansWithAnnotation.values()) {
            AnimalAnnotation annotation = o.getClass().getAnnotation(AnimalAnnotation.class);
            map.put(annotation.value().getType(), (IEatService) o);
        }
    }

    public IEatService getIeatService(AnimalEnum animalEnum) {
        return map.get(animalEnum.getType());
    }
}
