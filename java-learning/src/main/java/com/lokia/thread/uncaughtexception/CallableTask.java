package com.lokia.thread.uncaughtexception;

import java.util.concurrent.Callable;

public class CallableTask implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Integer.parseInt("1"));
        System.out.println(Integer.parseInt("2"));
        System.out.println(Integer.parseInt("3"));
        System.out.println(Integer.parseInt("twe"));
        return null;
    }
}
