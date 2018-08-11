package com.lokia.thread.log;

import com.lokia.IoUtils;
import com.lokia.thread.threadpool.CustomThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 将会有多个log生产者
 *
 * @author gushu
 * @data 2018/8/11
 */
public class LogProducer {

    private int producerNum;
    private ExecutorService producerPool;
    private LogConsumer consumer;
    private int round = 3;

    public LogProducer(int producerNum, LogConsumer consumer) {
        this.producerNum = producerNum;
        this.consumer = consumer;
        CustomThreadFactory threadFactory = new CustomThreadFactory();
        producerPool = Executors.newFixedThreadPool(producerNum,threadFactory);
    }


    public void start() {

        System.out.println("How many logs will be produced: " + producerNum*round);
        List<Callable<Void>> taskList = new ArrayList<>();

        for(int roundIndex = 0;roundIndex< round; roundIndex++){

            if(roundIndex == round -1){
                consumer.stop();
            }

            for (int i = 0; i < producerNum; i++) {
                ProducerThread task = new ProducerThread(i);
                taskList.add(task);

            }
            try {
                producerPool.invokeAll(taskList);
//            producerPool.invokeAny(taskList);
//            consumer.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // sleep before starting next round
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stop();
    }

    public  void stop(){
        producerPool.shutdown();
        try {
            producerPool.awaitTermination(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class ProducerThread implements Callable<Void> {

        int producerId;

        public ProducerThread(int producerId) {
            this.producerId = producerId;
        }

        @Override
        public Void call() {
            try{
                String msg = generateMsg();
                consumer.log(msg);
            }catch (IllegalArgumentException  e){
               System.err.println(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }
//            Thread.sleep(1);
            return null;
        }

        private String generateMsg() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < IoUtils.MSG_LOOP_COUNT; i++) {
                result.append(producerId);
            }
            result.append("\n");
            return result.toString();
        }
    }
}
