package com.lokia.spring.service.beaninterface;

import com.lokia.spring.service.beaninterface.impl.TransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanAnnotationInterfaceServiceTest {

    /**
     * @Autowire 对应的接口，如果有多个实现，那么它是按照变量的名字来的
     */
//    @Autowired
//    private TransferService transferService; // 这个会找 name为'transferService'的 TransferService的实现类
//    private TransferService transferService2; // 这个会找 name为'transferService2'的 TransferService的实现类

//    @Autowired
//    private TransferServiceImpl transferService2;

        @Autowired
    private TransferServiceImpl getTransferServiceImpl1;

    public void test() {
        System.out.println(getTransferServiceImpl1.getName());
        getTransferServiceImpl1.test();
    }

}
