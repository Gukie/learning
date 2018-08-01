package com.lokia.spring.config;

import com.lokia.spring.beans.TestBean1;
import com.lokia.spring.beans.TestBean2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Configuration
public class LiteModeBeanConfig {
//    @Order(1)
    @Bean(value = "lite-TestBean1")
    @Primary
    public TestBean1 getTestBean1() {
        TestBean1 result = new TestBean1();
        result.setName("lite-TestBean1");
        TestBean2 testBean2 = getTestBean2();
        // 这里将不会被AOP代理，因为 当前是lite 模式
        testBean2.getAge();
        System.out.println("lite-getTestBean1:"+testBean2);
        return result;
    }

    @Bean(value = "lite-TestBean1-1")
    public TestBean1 getTestBean11() {
        TestBean1 result = new TestBean1();
        result.setName("lite-TestBean11");
        TestBean2 testBean2 = getTestBean2();
        // 这里将不会被AOP代理，因为 当前是lite 模式
        testBean2.getAge();
        System.out.println("lite-getTestBean1-1:"+testBean2);
        return result;
    }

    @Bean("lite-TestBean2")
    public TestBean2 getTestBean2() {
        TestBean2 result = new TestBean2();
        result.setName("lite-TestBean2");
        return result;
    }
}
