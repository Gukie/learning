package com.lokia.dubbo;

import com.lokia.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringServiceConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        applicationContext.start();

        UserService userService = (UserService) applicationContext.getBean("userService");
        String userName = userService.getName();
        System.out.println("successfully get name:"+userName);
    }
}
