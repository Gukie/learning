package com.lokia.thread.uncaughtexception;

/**
 * @author gushu
 * @data 2018/8/11
 */
public class RunnableTask implements  Runnable {
    @Override
    public void run() {

        System.out.println(100/2);
        System.out.println(100/3);
        System.out.println(100/0);
        throw new RuntimeException("runtime....");
    }
}
