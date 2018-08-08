package com.lokia.thread.threadpool;

import java.util.concurrent.*;
/**
 * @author gushu
 * @data 2018/8/8
 */
public class ThreadPoolExecutorTest {


    public static void main(String[] args) {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = availableProcessors+1;
        int maximumPoolSize = corePoolSize * 2;
        long keepAliveTime = 10;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new CustomThreadFactory();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();


        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.MILLISECONDS,blockingQueue,threadFactory,rejectedExecutionHandler);;
        customThreadPoolExecutor.submit(new FileCreateAndMsgWriteTask());

        try {
            customThreadPoolExecutor.awaitTermination(6,TimeUnit.SECONDS);
//            customThreadPoolExecutor.awaitTermination()
            System.out.println("normally exit");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
