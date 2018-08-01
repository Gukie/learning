package com.lokia.spring.aop.custom;

import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @data 2018/7/23
 */
@Service
public class CustomInvocationServiceImpl implements CustomInvocationService {
    public void test(){
        System.out.println("***** CustomInvocationServiceImpl ******");
    }
}
