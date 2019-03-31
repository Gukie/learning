package com.lokia.thread.uncaughtexception;

//import jdk.internal.misc.Unsafe;

//import sun.misc.Unsafe;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceUEHTest {


    public static void main(String[] args) {

        int threadPoolSize = 1;
        testSubmitRunnable(threadPoolSize);
//        testExecuteRunnable(threadPoolSize);
    }

    private static void testSubmitRunnable(int threadPoolSize) {

        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(threadPoolSize);

        // 这里会=将 runnable 进行包装，在真正执行的时候即使有异常发生也会被抹掉，故不会被UncaughtExceptionHandler捕获到，只有在future.get()的时候才能看到
        // 可以查看 FutureTask的run方法
        Future future = threadPoolExecutor.submit(new RunnableTask());
        Object object = null;
        try {
            object = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) { // 这里的信息其实是真正执行的过程中产生的异常
            e.printStackTrace();
        }
        System.out.println(object);

    }

    private static void testExecuteRunnable(int threadPoolSize) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(threadPoolSize);
        // execute 不会被包装，故里面发生的异常不会被抹掉，会被UncaughtExceptionHandler捕获到
        threadPoolExecutor.execute(new RunnableTask());

    }

    private static ThreadPoolExecutor getThreadPoolExecutor(int threadPoolSize) {
        return new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 30, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(), new ThreadFactory() {

//                private volatile int threadNum = 0;
//                private Unsafe unsafe = Unsafe.getUnsafe();
            private AtomicInteger threadNum = new AtomicInteger(0);
                private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new MyUncaughtExceptionHandler();

                @Override
                public Thread newThread(Runnable r) {
//                    Field field = null;
//                    try {
//                        field = this.getClass().getDeclaredField("threadNum");
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    }
//                    unsafe.getAndAddInt(this,unsafe.objectFieldOffset(field),1);
                    Thread thread= new Thread(r,"testSubmitRunnable-"+threadNum.getAndDecrement());

                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                    return thread;
                }
            }, (r, executor) -> System.out.println("do nothing..."));
    }
}
