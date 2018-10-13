package com.lokia.refelection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class RefelectionTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ReflectionObject reflectionObject = new ReflectionObject();


        Class clazz = reflectionObject.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {

            Class<?>[] paramTypes = method.getParameterTypes();
            Object[] params = null;
            if(paramTypes!=null && paramTypes.length>0){
                params = new Object[paramTypes.length];
                int i = 0;
                for(Class<?> paramTypeClss : paramTypes){
                    params[i] = paramTypeClss.getDeclaredConstructor().newInstance();
                            i++;
                }
            }
            Object result = null;
           if(Modifier.isStatic(method.getModifiers()) ){
               result = method.invoke(null, params);

           }else{
              result= method.invoke(reflectionObject,params);
           }

           System.out.println("result:"+result);
        }
    }
}
