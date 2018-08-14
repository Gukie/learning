package com.lokia.java8;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gushu
 * @data 2018/8/14
 */
public class Person {


    public enum Sex {
        MALE, FEMALE
    }

    int age;
    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;


    public void printPerson() {
    }

    public static List<Person> createRoster() {
        return new ArrayList<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
