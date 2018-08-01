package com.lokia.spring.aop.custom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gushu
 * @data 2018/7/23
 */
public class CustomInvocationHandler implements InvocationHandler {

    private Object targetObj = null;
    public CustomInvocationHandler(Object targetObj) {
        this.targetObj = targetObj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        enhancedCode();
        Object ret = method.invoke(targetObj, args);
        return ret;
    }

    private void enhancedCode() {
        System.out.println(" enhanced code invoke...");
    }
}
