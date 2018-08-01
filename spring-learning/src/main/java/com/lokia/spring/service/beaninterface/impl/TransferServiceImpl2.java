package com.lokia.spring.service.beaninterface.impl;

import com.lokia.spring.service.beaninterface.TransferService;
import lombok.Data;

@Data
public class TransferServiceImpl2 implements TransferService {

    private String name;
    @Override
    public void test() {
        System.out.println("TransferServiceImpl-1:");
    }
}
