package com.lokia.basic.inherit;

/**
 * @author gushu
 * @data 2018/8/2
 */
public class Child extends Parent{
    String name = "Child";
    int age = 20;
    String sex = "F";

    @Override
    public int getAge() {
        return age+2;
    }
}
