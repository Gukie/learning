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

    private String name;
    private String city;
    private String sex;
    private int age;
}
