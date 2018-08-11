package com.lokia.thread.threadpool;

import com.lokia.thread.threadpool.task.FileCreateAndMsgWriteTask;
import com.lokia.thread.threadpool.task.UncaughtExceptionHandlerTestTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * @author gushu
 * @data 2018/8/8
 */
public class ThreadPoolExecutorTest {

    private static int thread_number = 5;

    public static void main(String[] args) {

        System.out.println("available cpu: "+Runtime.getRuntime().availableProcessors());
        System.out.println();


        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = availableProcessors+1;
        int maximumPoolSize = corePoolSize * 2;
        long keepAliveTime = 10;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new CustomThreadFactory();
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();


        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,blockingQueue,threadFactory,rejectedExecutionHandler);;

//        testUncaughtException(customThreadPoolExecutor);
        for(int i = 0;i< thread_number;i++){
            FutureTask<Void> task = new FutureTask<>(new FileCreateAndMsgWriteTask());
            customThreadPoolExecutor.submit(task);
        }

        try {
            customThreadPoolExecutor.shutdown();
            customThreadPoolExecutor.awaitTermination(20,TimeUnit.MINUTES); // before this invoke, the shutdown should be invoke, otherwise it will wait the timeout to terminate.
//            customThreadPoolExecutor.awaitTermination()
            System.out.println("normally exit");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("threadPoolExecutor isShutDown:"+customThreadPoolExecutor.isShutdown());
            System.out.println("threadPoolExecutor isTerminated:"+customThreadPoolExecutor.isTerminated());
            System.out.println("threadPoolExecutor isTerminating:"+customThreadPoolExecutor.isTerminating());

        }
    }

    private static void testUncaughtException(CustomThreadPoolExecutor customThreadPoolExecutor) {
        List<FutureTask<Void>> taskList = new ArrayList<>();
        for(int i = 0;i< thread_number;i++){
//            FutureTask<Void> task = new FutureTask<Void>(new FileCreateAndMsgWriteTask());
//            customThreadPoolExecutor.submit(task);
//            customThreadPoolExecutor.execute(task);

//            taskList.add(task);


            FutureTask<Void> task = new FutureTask<Void>(new UncaughtExceptionHandlerTestTask(),null);
//            customThreadPoolExecutor.execute(new UncaughtExceptionHandlerTestTask());
            customThreadPoolExecutor.submit(new UncaughtExceptionHandlerTestTask());

        }

//        for(FutureTask<Void> task: taskList){
//            try {
//                Object resutl = task.get();
//                System.out.println(resutl);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
    }


}
