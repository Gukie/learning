package com.lokia.thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushu
 * @data 2018/8/8
 */
public class CustomThreadFactory implements ThreadFactory {

    AtomicInteger threadCount = new AtomicInteger(1);

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "lokia-thread-" + threadCount.addAndGet(1));
        return thread;
    }
}
