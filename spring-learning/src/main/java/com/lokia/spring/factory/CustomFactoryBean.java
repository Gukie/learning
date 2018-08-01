package com.lokia.spring.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Component
public class CustomFactoryBean implements FactoryBean<FactoryBean1> {
    @Nullable
    @Override
    public FactoryBean1 getObject() throws Exception {
        // 这里可以定义复杂的 bean的创建
        FactoryBean1 result = new FactoryBean1();
        result.setName("created from factoryBean");
        return result;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return FactoryBean1.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
