package com.lokia.spring.annotation.custom;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * @author gushu
 * @data 2018/7/23
 */
public class CustomBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanNam = BeanDefinitionReaderUtils.generateBeanName(definition,registry);
        return "custome#"+beanNam;
    }
}
