package com.lokia.io.bytes;


import java.util.concurrent.CountDownLatch;

public class OutputStreamTest {

    public static void main(String[] args) {

        int loops = 5;
        CountDownLatch countDownLatch = new CountDownLatch(loops);
        for(int i =0;i<loops;i++){
            Thread thread = new Thread(new OutputStreamThread(countDownLatch),"output-stream-thread"+(i+1));
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished output stream test...");


    }
}
