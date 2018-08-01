package com.lokia.spring.aop.custom;

import java.lang.reflect.Proxy;

/**
 * @author gushu
 * @data 2018/7/23
 */
public class CustomProxyFactory {

    public static Object createProxyObj(Object targetObj){
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),targetObj.getClass().getInterfaces(),new CustomInvocationHandler(targetObj));
    }
}
