package com.lokia.thread.log;

import com.lokia.IoUtils;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 只会有一个消费者，即只会开一个线程来处理
 *
 * @author gushu
 * @data 2018/8/11
 */
public class LogConsumer {

    private BlockingQueue<String> queue;
    private LoggerThread logger;
    private boolean isShutDown;
    private AtomicInteger remainingLogNum = new AtomicInteger(0);

    // file


    public LogConsumer() {
        queue = new LinkedBlockingQueue(32);
        logger = new LoggerThread();
    }

    public void start() {
        logger.start();
    }

    public void stop() {
        synchronized (this){
            isShutDown = true;
        }
        logger.interrupt();
    }

    public void log(String log) {
        synchronized (this){
            if(isShutDown){
                throw new IllegalArgumentException("log-consumer is shut down");
            }
        }
        try {
            queue.put(log);
            remainingLogNum.addAndGet(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class LoggerThread extends Thread {
        @Override
        public void run() {
            File file = IoUtils.getFile(IoUtils.getIoFileDir(), "output.log");
            BufferedWriter writer ;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                while (true) {
                    try {
                        synchronized (LogConsumer.this){
                            if(isShutDown && remainingLogNum.get()==0){
                                break;
                            }
                        }
                        String msg = queue.take();
                        writer.write("consumed: " + msg);
                        writer.flush();
                        remainingLogNum.decrementAndGet();
                        // 降低 消费者的消费速度
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        System.err.println("interrupted, remaining log: "+remainingLogNum.get());
//                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                // handle un-consumed messages
                if (!queue.isEmpty()) {
                    writer.write(" *************\n");
                    while (!queue.isEmpty()) {
                        String msgItem = queue.poll();
                        if (msgItem != null) {
                            writer.write("not consumed: " + msgItem);
                        }
                    }
                }
                // release resource
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                System.out.println("Consumer exit....");
            }
        }
    }
}
