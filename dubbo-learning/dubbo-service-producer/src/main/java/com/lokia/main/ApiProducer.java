package com.lokia.main;

import com.lokia.service.UserService;
import com.lokia.service.impl.UserServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;


public class ApiProducer {

    public static void main(String[] args) throws IOException {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-service-producer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("localhost");
        registryConfig.setPort(2181);
        registryConfig.setClient("zkclient");


        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("zookeeper");
//        protocolConfig.setPort(2181);

        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(new UserServiceImpl());
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("lokia-group");

        serviceConfig.export();

        System.in.read();
    }

}
