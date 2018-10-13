package com.lokia.proxy.dynamic.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler {

    private Object realObj;
    public LogHandler(Object realObj){
        this.realObj = realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy:"+proxy.getClass());
        System.out.println(method.getName()+" invoke start...");
        Object result = method.invoke(realObj,args);
        System.out.println(method.getName()+" invoke end...");
        return result;
    }
}
