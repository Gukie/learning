package com.lokia.thread.synclevel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *refer：https://howtodoinjava.com/java/multi-threading/object-vs-class-level-locking/
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
            // 锁住的是class对象，对实例是没有影响对； 锁住class对象对时候，只对静态方法有影响，对实例方法没有影响；
            for (int i = 0; i < 5; i++) {
                SyncObj syncObj = new SyncObj((i + 1) + "");
                pool.submit(() -> {
                    syncObj.syncOnClass();

                });
                pool.submit(()-> syncObj.syncOnInstance());
            }
        }


        try {
            pool.shutdown();
            pool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all have finished...");

    }


}
