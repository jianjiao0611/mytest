package com.jjtest.user.service;

import com.jjtest.tool.util.SpringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object =  object;
    }

    public MyInvocationHandler(Class object) {
        this.object =  SpringUtils.getBean(object);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("MyInvocationHandler invoke begin");
        System.out.println("proxy: " + proxy.getClass().getName());
        System.out.println("method: " + method.getName());
        for (Object o : args) {
            System.out.println("arg: " + o);
        }
        //通过反射调用 被代理类的方法
        Object invoke = method.invoke(object, args);
        System.out.println("MyInvocationHandler invoke end");
        return invoke;
    }
}
