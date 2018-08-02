package com.lokia.spring.main;

import com.lokia.spring.beanorder.BeanOrderTestService;
import com.lokia.spring.beans.TestBean1;
import com.lokia.spring.factory.FactoryBean1;
import com.lokia.spring.service.BeanAnnotationTestService;
import com.lokia.spring.service.beaninterface.BeanAnnotationInterfaceServiceTest;
import com.lokia.spring.beans.subparent.SubBean;
import com.lokia.spring.service.SubParentServiceTest;
import com.lokia.spring.service.methodinjection.ApplicationContextMIServiceTest;
import com.lokia.spring.service.methodinjection.CglibLookupMIServiceTest;
import com.lokia.spring.service.methodinjection.replace.ReplacementMIServiceTest;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class SpringTestMain {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println();
        Map<String,TestBean1> nameBeanMap = context.getBeansOfType(TestBean1.class);
        for(Map.Entry<String,TestBean1> entry: nameBeanMap.entrySet()){
            System.out.println(entry.getKey()+","+entry.getValue());
        }

        System.out.println();
        System.out.println("TestBean1 instance primary name...");
        TestBean1 testBean1 = context.getBean(TestBean1.class);
        System.out.println(testBean1.getName());


        // test service invoke
        System.out.println();
        System.out.println("TestBean1 instance primary name...");
        BeanAnnotationTestService beanAnnotationTestService = context.getBean(BeanAnnotationTestService.class);
        beanAnnotationTestService.printBeanName();

        System.out.println();
        System.out.println("test sub-parent service definition...");
        SubParentServiceTest subParentServiceTest = context.getBean(SubParentServiceTest.class);
        subParentServiceTest.test();

//        method injection
        System.out.println();
        System.out.println("test method injection with applicationContext...");
        ApplicationContextMIServiceTest methodInjectionServiceTestApplicationContext = context.getBean(ApplicationContextMIServiceTest.class);
        for(int i =0;i<3;i++){
            methodInjectionServiceTestApplicationContext.testAutowire();
        }
        System.out.println("------------");
        for(int i =0;i<3;i++){
            methodInjectionServiceTestApplicationContext.testMethodInject();
        }

        System.out.println();
        System.out.println("test method injection with lookup...");
        CglibLookupMIServiceTest lookup = context.getBean(CglibLookupMIServiceTest.class);
        for(int i =0;i<3;i++){
            lookup.test();
        }

        System.out.println();
        System.out.println("test method injection with replacement...");
        ReplacementMIServiceTest replacement = context.getBean(ReplacementMIServiceTest.class);
        for(int i =0;i<3;i++){
            System.out.println(replacement.generateStr());
            Thread.sleep(2);
        }

        System.out.println();
        System.out.println("test bean annotation with interface...");
        BeanAnnotationInterfaceServiceTest beanAnnotationInterfaceServiceTest = context.getBean(BeanAnnotationInterfaceServiceTest.class);
        beanAnnotationInterfaceServiceTest.test();

        System.out.println();
        System.out.println("test abstract sub-parent bean...");
        SubBean subBean = context.getBean(SubBean.class);
        System.out.println(subBean);
//        System.out.println(subBean.getAge());


//      test factory bean
        System.out.println();
        System.out.println("test factory bean...");
        FactoryBean factoryBean = (FactoryBean) context.getBean("&customFactoryBean");
        Class<?> cls = factoryBean.getObjectType();
        System.out.println(cls.getName());

        FactoryBean1 factoryBean1 = (FactoryBean1) context.getBean("customFactoryBean");
        System.out.println(factoryBean1.getName());

        // test bean order
        System.out.println();
        System.out.println("test order bean...");
        BeanOrderTestService beanOrderTestService = context.getBean(BeanOrderTestService.class);
        beanOrderTestService.testOrderInCollection();
        beanOrderTestService.testFetchSingleton();
        beanOrderTestService.testFetchPrimaryOne();

    }
}
