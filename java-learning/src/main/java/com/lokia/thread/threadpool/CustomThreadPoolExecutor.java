package com.lokia.thread.threadpool;

import com.lokia.thread.threadlocal.WorkerStartTimeThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author gushu
 * @data 2018/8/8
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    WorkerStartTimeThreadLocal startTimeThreadLocal = new WorkerStartTimeThreadLocal();

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
    }


    @Override
    public void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTimeThreadLocal.get(); // 这里会将 initialValue()中的值设置到threadLocal中去

    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Long start = startTimeThreadLocal.get();
        Long end = System.currentTimeMillis();

        String threadName = Thread.currentThread().getName();
        System.out.println("thread pool executor calculated, " + threadName + ", time consumed: " + (end - start));

        startTimeThreadLocal.remove();
        if(t !=null){
//            Thread.currentThread().un
            System.err.println("********************** CustomThreadPoolExecutor afterExecute get UncaughtException, thread-name;"+Thread.currentThread().getName());
        }
    }

    @Override
    public void terminated() {
        super.terminated();
        String threadName = Thread.currentThread().getName();
        System.out.println("thread pool executor calculated, " + threadName + ", terminated.");
    }


}
