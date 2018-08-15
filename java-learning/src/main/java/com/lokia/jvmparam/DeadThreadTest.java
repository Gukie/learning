package com.lokia.jvmparam;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author gushu
 * @data 2018/8/15
 */
public class DeadThreadTest {

    CyclicBarrier barrier = new CyclicBarrier(2);
    static CountDownLatch countDownLatch = new CountDownLatch(2);
    static int loops = 220;


    public static void main(String[] args) {

        DeadThreadTest deadThreadTest = new DeadThreadTest();
        Object obj1 = new Object();
        Object obj2 = new Object();
        for (int i = 0; i < loops; i++) {

//            new Thread(deadThreadTest.new Sync(obj1, obj2)).start();
//            new Thread(deadThreadTest.new Sync(obj2, obj1)).start();
            new Thread(deadThreadTest.new Sync(1, 2)).start();
            new Thread(deadThreadTest.new Sync(2, 1)).start();

//            barrier.reset();
        }


//        new Thread(deadThreadTest.new Sync(1, 2)).start();
//        new Thread(deadThreadTest.new Sync(2, 1)).start();
//        System.out.println("count down...");
        countDownLatch.countDown();
        countDownLatch.countDown();
//        System.out
    }

    class Sync implements Runnable {

        //        private int first;
//        private int second;
        private Object first;
        private Object second;

        Sync(Object first, Object second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void run() {

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (first) {
                synchronized (second) {
                    System.out.println("fist:" + first + ",second:" + second);
                }
            }
        }
    }

}
