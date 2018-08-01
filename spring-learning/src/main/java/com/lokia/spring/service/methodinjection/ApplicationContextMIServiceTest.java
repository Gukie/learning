package com.lokia.spring.service.methodinjection;

import com.lokia.spring.beans.SingletonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextMIServiceTest {

    // 如果用这种方式注入，那么singletonBean只有一个，不会每一次都一个新的Bean
    @Autowired
    private SingletonBean singletonBean;
    @Autowired
    private ApplicationContext context;

    public void testAutowire(){
        System.out.println(singletonBean);
        System.out.println("name:"+singletonBean.getName());
        singletonBean.setName(String.valueOf(System.currentTimeMillis()));
    }

    public void testMethodInject(){
        SingletonBean currBean =  getSingletonBean();
        System.out.println(currBean);
        System.out.println("name:"+currBean.getName());
        currBean.setName(String.valueOf(System.currentTimeMillis()));
    }

    protected SingletonBean getSingletonBean(){
        return context.getBean(SingletonBean.class);
    }
}
