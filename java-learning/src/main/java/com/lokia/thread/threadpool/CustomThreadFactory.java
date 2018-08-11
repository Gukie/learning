package com.lokia.thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushu
 * @data 2018/8/8
 */
public class CustomThreadFactory implements ThreadFactory, Thread.UncaughtExceptionHandler {

    AtomicInteger threadCount = new AtomicInteger(0);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "lokia-thread-" + threadCount.addAndGet(1));
//        thread.setUncaughtExceptionHandler((t, e) -> {
//            System.err.println("***************alil******* CustomThreadFactory uncaughtException:"+e.getMessage());
////                e.printStackTrace();
//        });
        thread.setUncaughtExceptionHandler(this);
        return thread;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("********************** CustomThreadFactory uncaughtException:"+t.getName());
//        e.printStackTrace();
    }
}
