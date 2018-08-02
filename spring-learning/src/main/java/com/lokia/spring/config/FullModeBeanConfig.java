package com.lokia.spring.config;

import com.lokia.spring.beans.TestBean1;
import com.lokia.spring.beans.SingletonBean;
import com.lokia.spring.beans.TestBean2;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class FullModeBeanConfig {

//    @Order(2)
    @Bean(value = "full-TestBean1",autowire = Autowire.BY_NAME)
    public TestBean1 getTestBean1(){
        TestBean1 result = new TestBean1();
        result.setName("full-TestBean1-0");

        TestBean2 testBean2 = getTestBean2();
        // // 这个阶段的 setName 方法的调用会被 AOP拦截，因为此时 TestBean2 已经在 Spring 容器中了
        testBean2.setName("dd");
        System.out.println("full-getTestBean1:"+testBean2);
        return result;
    }

    @Bean(value = "full-TestBean1-1",autowire = Autowire.BY_NAME)
    @Order(2)
    public TestBean1 getTestBean11(){
        TestBean1 result = new TestBean1();
        result.setName("full-TestBean1-1");

        TestBean2 testBean2 = getTestBean2();
        // // 这个阶段的 setName 方法的调用会被 AOP拦截，因为此时 TestBean2 已经在 Spring 容器中了
        testBean2.setName("dd");
        System.out.println("full-getTestBean1-1:"+testBean2);
        return result;
    }

    @Bean("full-TestBean2")
    public TestBean2 getTestBean2(){
        TestBean2 result = new TestBean2();
        // 这个阶段的 setName 方法的调用不会被 AOP拦截，因为此时 TestBean2 还没有放到 Spring 容器中
        result.setName("full-TestBean2");
        return result;
    }

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SingletonBean getSingletonBean(){
        SingletonBean singletonBean = new SingletonBean();
        singletonBean.setName("singletonBean-1");
        return singletonBean;
    }
}
