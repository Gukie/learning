package com.lokia.spring.aop.cglib;

import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @data 2018/7/23
 */
@Service
public class CglibProxyServiceImpl {

    private String name = "hello";

    public void test(){
        System.out.println("cglib proxy test: "+name);
    }
}
