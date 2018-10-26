package com.lokia.basic.generics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * refer: https://blog.csdn.net/briblue/article/details/76736356
 */
public class WildMatchGenericsTest {

    public static void main(String[] args) {
        WildMatchGenericsTest instance = new WildMatchGenericsTest();

        List<String> strList = new ArrayList<>();
        strList.add("hello");
        instance.testwild(strList);


        List<Integer> integerList = new ArrayList<>();
        integerList.add(100);
        instance.testExtend(integerList);

        List<Number> numbers = new ArrayList<>();
        numbers.add(100);
        instance.testSuper(numbers);

        System.out.println("*******************");
        System.out.println("List<String>.getClass() == List<Integer>.getClass(): "+ (strList.getClass() == integerList.getClass()));

        GenericsBean<String> genericsBean = new GenericsBean<>();
        printClassInfo(genericsBean);
        GenericsExtendBean<Long> genericsExtendBean = new GenericsExtendBean<>();
        printClassInfo(genericsExtendBean);



    }

    private static<T> void printClassInfo(T genericsBean) {
        System.out.println(genericsBean.getClass().getName());

//        Field[] fields = genericsBean.getClass().getFields();
        Field[] fields = genericsBean.getClass().getDeclaredFields();
        if(fields !=null && fields.length>0){
            for (Field field : fields) {
                System.out.println(field.getName()+":"+field.getType().getName());

            }
        }
    }

    public void testwild(List<?> lst ){

        Object object = lst.get(0);
//        lst.add(new Object());

        System.out.println("testwild - numbers class name:"+lst.getClass().getName());
        System.out.println("testwild - element class name:"+lst.get(0).getClass().getName());

    }


    /**
     * numbers中的元素是 Number的子类，所以add的时候，不能确定会是什么类型，故不能使用add方法
     *
     * 而获取的时候却不一样，因为Number是 容器中的 类型的父类，故可以用它来获取
     * @param numbers
     */
    public void testExtend(List<? extends  Number> numbers){

//        numbers.add(100L);
        Number item = numbers.get(0);

        String className = numbers.getClass().getName();
        String subClass = numbers.get(0).getClass().getName();
        System.out.println("testExtend - numbers class name:"+className);
        System.out.println("testExtend - element class name:"+subClass);

    }


    /**
     * 能确定的是， numbers里面的元素的类型，要没是Number自己，要么就是Number的父类；
     * 其中Number是确定的，所以可以将其作为元素加进去，不会报错; 除此之外其他的都不清楚其具体的类型，所以会报错
     *
     * 而获取的时候，由于获取到的元素，不知道其是什么，单可以确定的是Object是所有类型的父类，故可以他来获取（多态）
     * @param numbers
     */
    public void testSuper(List<? super  Number> numbers){
        numbers.add(100); //
//        numbers.add( new Object());

//        Number item = (Number) numbers.get(0);
        Object object = numbers.get(0);


        System.out.println("testSuper - numbers class name:"+numbers.getClass().getName());
        System.out.println("testSuper - element class name:"+numbers.get(0).getClass().getName());

    }
}
