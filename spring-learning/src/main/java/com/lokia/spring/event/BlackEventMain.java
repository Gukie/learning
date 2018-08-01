package com.lokia.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author gushu
 * @data 2018/7/24
 */
public class BlackEventMain {

    public static void main(String[] args) {

//        // manually trigger
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.scan("com.lokia.spring.event");
//        context.refresh();
//
//        BlackListEventPublisher eventPublisher = context.getBean(BlackListEventPublisher.class);
//        for (int i = 0; i < 3; i++) {
//            eventPublisher.publishBlackItem("" + i);
//        }
//        context.stop();
//        context.start();
//        context.close();


        // only contextRefreshedEvent will be triggered. other event should be triggered manually
        ApplicationContext context = new GenericXmlApplicationContext("spring.xml");
        BlackListEventPublisher eventPublisher = context.getBean(BlackListEventPublisher.class);
        for (int i = 0; i < 3; i++) {
            eventPublisher.publishBlackItem("" + i);
        }
    }
}
