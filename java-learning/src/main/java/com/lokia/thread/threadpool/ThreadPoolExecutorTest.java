package com.lokia.thread.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * @author gushu
 * @data 2018/8/8
 */
public class ThreadPoolExecutorTest {

    private static int thread_number = 10;

    public static void main(String[] args) {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = availableProcessors+1;
        int maximumPoolSize = corePoolSize * 2;
        long keepAliveTime = 10;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new CustomThreadFactory();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();


        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,blockingQueue,threadFactory,rejectedExecutionHandler);;

//        List<FutureTask<Void>> taskList = new ArrayList<>();
        for(int i = 0;i< thread_number;i++){
            FutureTask<Void> task = new FutureTask<Void>(new FileCreateAndMsgWriteTask());
            customThreadPoolExecutor.submit(task);
        }

        try {
            customThreadPoolExecutor.awaitTermination(6,TimeUnit.SECONDS);
//            customThreadPoolExecutor.awaitTermination()
            System.out.println("normally exit");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("threadPoolExecutor isShutDown:"+customThreadPoolExecutor.isShutdown());
            System.out.println("threadPoolExecutor isTerminated:"+customThreadPoolExecutor.isTerminated());
            System.out.println("threadPoolExecutor isTerminating:"+customThreadPoolExecutor.isTerminating());
        }
    }


}
