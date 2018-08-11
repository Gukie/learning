package com.lokia.thread.uncaughtexception;

/**
 * @author gushu
 * @data 2018/8/11
 */
public class UncaughtExceptionTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadTest());
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("uncaught exception*****: ");
            e.printStackTrace();
        });
        thread.start();
    }
}
