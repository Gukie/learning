package com.lokia.basic.inherit;

/**
 * @author gushu
 * @data 2018/8/2
 */
public class InheritMain {

    public static void main(String[] args) {
        Parent child = new Child();

        System.out.println(child.age);
        System.out.println(child.name);

        child.age = 26;
        System.out.println(child.getAge());


    }
}
