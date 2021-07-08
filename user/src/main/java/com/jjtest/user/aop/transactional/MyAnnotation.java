package com.jjtest.user.aop.transactional;

import java.lang.annotation.*;

/**
 * 自定义事务注释
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyAnnotation {
    //自定义注解的属性
    int id() default 0;

    String name() default "默认名称";

    String[] arrays() default {};

    String title() default "默认标题";
}
