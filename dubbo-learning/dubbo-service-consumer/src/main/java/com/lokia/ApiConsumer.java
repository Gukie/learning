package com.lokia;

import com.lokia.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class ApiConsumer {
    public static void main(String[] args) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("localhost:9090");

        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setGroup("lokia-group");
        referenceConfig.setVersion("1.0.0");


        Object result = referenceConfig.get();
        System.out.println(result);

    }
}
