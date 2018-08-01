package com.lokia.spring.service.subparent;

import com.lokia.spring.beans.TestBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SubService extends ParentService {

    @Autowired
    private ApplicationContext context;

    public void test(){
        System.out.println("sub test");
        TestBean1 testBean1 = context.getBean(TestBean1.class);
        System.out.println(testBean1.getName());
    }
}
