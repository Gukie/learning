package com.lokia.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Component
//@Lazy
public class CustomBeanPostProcessor implements BeanPostProcessor{

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
//        System.out.println("before initialization bean:"+beanName);
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(bean instanceof  com.lokia.spring.beans.subparent.SubBean){
            System.out.println("after initialization bean:"+beanName);
        }
        return bean;
    }

}
