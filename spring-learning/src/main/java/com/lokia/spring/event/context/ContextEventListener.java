package com.lokia.spring.event.context;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/24
 */
@Component
public class ContextEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent){
            System.out.println("***** context refreshed event ***** ");
        }
        if(event instanceof ContextStartedEvent){
            System.out.println("***** context started event ***** ");
        }
        if(event instanceof ContextStoppedEvent){
            System.out.println("***** context stopped event ***** ");
        }
        if(event instanceof ContextClosedEvent){
            System.out.println("***** context closed event ***** ");
        }
    }
}
