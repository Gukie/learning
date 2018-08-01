package com.lokia.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

//@Configuration
@Component
@Aspect
public class AopTest {

    private final static  String basePackage = "com.lokia";

    //    @Pointcut(value = "execution(* com.alibaba.campus.iengine.service.springtest.beans.TestBean2.*(..))")
//    @Around("execution(* com.TestBean2.*(..))")
    public Object invokeTestBean2(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String name =joinPoint.getTarget().getClass().getName()+"#"+ method.getName();
        String invokeLocation = getInvokeLocation();
        try{
            System.out.println(invokeLocation+" invoke "+ name+" starting...");
            Object obj =    joinPoint.proceed();
            System.out.println(invokeLocation+" invoke "+ name+" ending...");
            return obj;
        }catch (Exception e){
             return e;
        }
    }

    private String getInvokeLocation() {
        StackTraceElement[]  stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements==null || stackTraceElements.length==0){
            return "";
        }
        String currentCls = AopTest.class.getName();
        for(StackTraceElement item: stackTraceElements){
            String declaringCls = item.getClassName();
            // com.AopTest
            if(Objects.equals(declaringCls,currentCls)){
                continue;
            }
            if(declaringCls.startsWith(basePackage) && item.getFileName().endsWith(".java")){
                return declaringCls+"."+item.getMethodName();
            }
        }
        return "";
    }

    @Around("execution(* com.lokia.spring.aop.cglib.CglibProxyServiceImpl.*(..))")
    public void invokeCglib(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("invokeCglib start...");
        joinPoint.proceed();
        System.out.println("invokeCglib end...");
    }
    @Around("execution(* com.lokia.spring.aop.jdk.JdkDynamicProxyService.*(..))")
    public void invokeJdkDynamic(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("invoke jdk dynamic start...");
        joinPoint.proceed();
        System.out.println("invoke jdk dynamic end...");
    }
}
