package com.lokia.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author gushu
 * @data 2018/8/6
 */
public class MaxThreadNumTest {

    public static void main(String[] args) {

        int threadNum = 0;
        try{
            while (true) {
                threadNum++;
                System.out.println(threadNum);
                Thread thread = new Thread(new NoEndRunnable());
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private static class NoEndRunnable implements Runnable {
        private CountDownLatch countDownLatch = new CountDownLatch(1);

        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
