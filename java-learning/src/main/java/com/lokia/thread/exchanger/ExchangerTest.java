package com.lokia.thread.exchanger;


import java.util.concurrent.Exchanger;

public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<ExchangeObj> exchanger = new Exchanger<>();

        ExchangeObj obj1 = new ExchangeObj();
        obj1.setName("pencil");

        ExchangeObj obj2 = new ExchangeObj();
        obj2.setName("chocolate");

        new Thread(() -> {
            try {
                obj1.setName(obj1.getName()+"-"+ java.lang.Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+":before exchange - "+obj1.getName());
                ExchangeObj myExObj =exchanger.exchange(obj1);
                System.out.println(Thread.currentThread().getName()+":after exchange - "+myExObj.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-1").start();
        new Thread(() -> {
            try {


                obj2.setName(obj2.getName()+"-"+ java.lang.Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+":before exchange - "+obj2.getName());
                ExchangeObj myExObj = exchanger.exchange(obj2);
                System.out.println(Thread.currentThread().getName()+":after exchange - "+myExObj.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-2").start();
    }
}
