package com.lokia.thread.log;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 应用场景： 一个log消费者，多个log生产者
 *
 * @author gushu
 * @data 2018/8/11
 */
public class LogTestMain {



    public static void main(String[] args) {
        Thread.currentThread().setName("log-test-main");
        int producerNum = 10;
//        CountDownLatch gun = new CountDownLatch(producerNum+1);

        LogConsumer consumer = new LogConsumer();
        LogProducer producer = new LogProducer(producerNum,consumer);

        consumer.start();
        producer.start();


//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // stop
//        consumer.stop();
//        producer.stop();
        System.out.println("exit normally....");
    }
}
