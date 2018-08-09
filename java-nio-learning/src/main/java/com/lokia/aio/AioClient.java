package com.lokia.aio;

import com.lokia.aio.thread.AioWriteTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AioClient {


    private final static int MSG_LOOPS = 99;
    private static AtomicInteger msgCount = new AtomicInteger(0);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        ExecutorService threadPool = Executors.newCachedThreadPool();



        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPool, threadNum);

        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open(channelGroup);
        Future<Void> futureTask = socketChannel.connect(new InetSocketAddress(AioUtils.HOST, AioUtils.PORT));
        while(futureTask.get()!=null){

        }

        for(int channelIndex = 1;channelIndex<= AioUtils.CLIENT_CHANNEL_NUM;channelIndex++){
            String msg  = generateMsg(channelIndex);
            AioWriteTask writeTask = new AioWriteTask(msg,socketChannel);
            Thread thread = new Thread(writeTask);
            thread.start();
            AioUtils.countDownLatch.countDown();
        }
    }

    private static String generateMsg(int channelIndex) {
        StringBuilder result = new StringBuilder(channelIndex);

        for(int i = 0;i< MSG_LOOPS;i++){
            result.append(channelIndex);
        }
        return result.toString();
    }


}
