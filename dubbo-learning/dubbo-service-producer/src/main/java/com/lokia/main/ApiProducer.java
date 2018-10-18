package com.lokia.main;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lokia.service.UserService;
import com.lokia.service.impl.UserServiceImpl;
import java.io.IOException;


public class ApiProducer {

    public static void main(String[] args) throws IOException {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service-producer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181"); // this address should be the one which can be connected, otherwise registry will fail.
        registryConfig.setClient("zkclient");



        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(new UserServiceImpl());
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setApplication(applicationConfig);
//        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("lokia-group");

        serviceConfig.export();

        System.out.println("successfully start provider...");
        System.in.read();
    }

}
