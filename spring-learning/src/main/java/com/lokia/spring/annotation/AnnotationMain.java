package com.lokia.spring.annotation;

import com.lokia.spring.annotation.service.AnnotationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gushu
 * @data 2018/7/23
 */
public class AnnotationMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationConfig.class);
        context.refresh();


        String beanNames[] =context.getBeanNamesForType(AnnotationService.class);
        for(String beanName: beanNames){
            System.out.println(beanName);
        }
    }
}
