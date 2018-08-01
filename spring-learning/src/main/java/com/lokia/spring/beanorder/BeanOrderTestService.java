package com.lokia.spring.beanorder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Service
public class BeanOrderTestService implements ApplicationContextAware {

    @Autowired
    List<OrderBean> orderBeanList;

    private ApplicationContext applicationContext;

    public void testOrderInCollection() {
        System.out.println("********* test *********");
        System.out.print(orderBeanList.size());
        for (OrderBean orderBean : orderBeanList) {
            System.out.println(orderBean.getName());
        }
    }

    public void testFetchSingleton() {
        System.out.println("********* testFetchSingleton *********");
        for (int i = 0; i < 3; i++) {
            OrderBean orderBean = applicationContext.getBean("protoTypeOrderBean3",OrderBean.class);
            System.out.println(orderBean+"-"+orderBean.getName());
        }
    }
    public void testFetchPrimaryOne() {
        System.out.println("********* testFetchPrimaryOne *********");
        for (int i = 0; i < 1; i++) {
            OrderBean orderBean = applicationContext.getBean(OrderBean.class);
            System.out.println(orderBean+"-"+orderBean.getName());
        }
        applicationContext.getBeanDefinitionNames();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
