package com.lokia.java8.bean;

import lombok.Data;

@Data
public class Student {

    public Student(){

    }

    public Student(String name, String city){
        setName(name);
        setCity(city);
    }

    public Student(String name,String city,String sex){
        setName(name);
        setCity(city);
        setSex(sex);
    }

    private String name;
    private String city;
    private String sex;
    private int age;
}
