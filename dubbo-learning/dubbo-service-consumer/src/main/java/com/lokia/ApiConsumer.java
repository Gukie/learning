package com.lokia;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.lokia.service.UserService;

public class ApiConsumer {
    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("zkclient");

        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setGroup("lokia-group");
        referenceConfig.setVersion("1.0.0");


        UserService user = (UserService) referenceConfig.get();
        System.out.println(user.getName());

    }
}
