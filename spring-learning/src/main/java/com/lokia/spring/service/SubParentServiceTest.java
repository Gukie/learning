package com.lokia.spring.service;

import com.lokia.spring.service.subparent.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubParentServiceTest {

    @Autowired
    private ParentService parentService;

    public  void test(){
        parentService.test();
    }
}
