package com.lokia.aio;

import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AioServer {




    public static void main(String[] args) throws InterruptedException {

        Object obj = new Object();

        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPool,30);
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }


        obj.wait();

    }
}
