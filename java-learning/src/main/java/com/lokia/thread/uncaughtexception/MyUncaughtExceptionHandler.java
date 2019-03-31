package com.lokia.thread.uncaughtexception;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("uncaught exception happens in thread["+t.getName()+"],"+e.getMessage());

    }
}
