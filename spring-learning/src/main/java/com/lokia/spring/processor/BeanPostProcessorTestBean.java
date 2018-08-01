package com.lokia.spring.processor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Component
public class BeanPostProcessorTestBean implements InitializingBean{
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanPostProcessorTestBean after properties set");
    }
}
