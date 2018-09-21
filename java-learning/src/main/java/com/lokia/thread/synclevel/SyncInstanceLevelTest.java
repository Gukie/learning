package com.lokia.thread.synclevel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *
 */
public class SyncInstanceLevelTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);


//        boolean tesInstanceLevel = true;
        boolean tesInstanceLevel = false;

        if (tesInstanceLevel) {
            // 不同的实例有不同的锁，所以他们的锁不会有冲突
            for (int i = 0; i < 5; i++) {
                SyncObj syncObj = new SyncObj((i + 1) + "");
                pool.submit(() -> {
                    syncObj.syncOnInstance();
                });
            }
        } else {
            System.out.println("************ sync on class level ************");
            // 同一个class的instance，只会有一个实例运行，这个实例的方法没有结束，其他实例都得等着
            // TODO 为什么只有线程1执行了，其他的不会执行
            for (int i = 0; i < 5; i++) {
                SyncObj syncObj = new SyncObj((i + 1) + "");
                pool.submit(() -> {
                    syncObj.syncOnClass();
                });
            }
        }


        try {
//            pool.shutdown();
            pool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            pool.shutdown();
        }

        System.out.println("all have finished...");

    }


}
