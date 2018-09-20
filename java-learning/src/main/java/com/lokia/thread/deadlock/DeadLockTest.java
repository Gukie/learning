package com.lokia.thread.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLockTest {

    private static  ExecutorService threadPool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        threadPool.submit(new DeadlockWorkerThread());
    }

    private static class DeadlockWorkerThread implements Runnable {
        @Override
        public void run() {
            threadPool.submit(() -> System.out.println("DeadlockWorkerThread run finished"));
        }
    }
}
