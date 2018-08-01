package com.lokia.spring.service;

import com.lokia.spring.beans.TestBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BeanAnnotationTestService {

    @Autowired
    @Qualifier("full-TestBean1")
    private TestBean1 fullTestBean1;

    @Autowired
    @Qualifier("lite-TestBean1")
    private TestBean1 litetestBean1;

    public void printBeanName(){
        String fullName = fullTestBean1.getName();
        String liteName = litetestBean1.getName();

        System.out.println("full:"+fullName);
        System.out.println("lite:"+liteName);
    }

}
