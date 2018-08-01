package com.lokia.spring.beanorder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Configuration
public class OrderBeanConfig {

    @Bean
    @Order(1)
    public OrderBean orderBean1(){
        OrderBean orderBean = new OrderBean();
        orderBean.setName("orderBean-1");
        return orderBean;
    }

    @Bean
    @Order(2)
    @Primary
    public OrderBean orderBean2(){
        OrderBean orderBean = new OrderBean();
        orderBean.setName("orderBean-2");
        return orderBean;
    }

    @Bean
    @Order(0)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OrderBean protoTypeOrderBean3(){
        OrderBean orderBean = new OrderBean();
        orderBean.setName("proto-type-orderBean");
        return orderBean;
    }
}
