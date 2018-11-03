package com.lokia.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringServiceProvider {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("provider.xml");
        applicationContext.start();

        System.out.println("spring provider start successfully...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
