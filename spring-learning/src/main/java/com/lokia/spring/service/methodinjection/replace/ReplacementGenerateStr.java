package com.lokia.spring.service.methodinjection.replace;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ReplacementGenerateStr implements MethodReplacer{

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        return System.currentTimeMillis()+"";
    }
}
