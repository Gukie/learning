package com.lokia.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AioClient {

    private final static int CLIENT_CHANNEL_NUM = 5;
    private final static int MSG_LOOPS = 3;
    private static AtomicInteger msgCount = new AtomicInteger(0);

    public static void main(String[] args) {
        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        ExecutorService threadPool = Executors.newCachedThreadPool();


        for(int channelIndex = 0;channelIndex< CLIENT_CHANNEL_NUM;channelIndex++){

            System.out.println();
            System.out.println("************ current channel: "+(channelIndex+1));
            AsynchronousChannelGroup channelGroup = null;
            AsynchronousSocketChannel socketChannel = null;
            try {
                channelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPool, threadNum);
                socketChannel = AsynchronousSocketChannel.open(channelGroup);
                Future<Void> futureTask = socketChannel.connect(new InetSocketAddress(AioUtils.HOST, AioUtils.PORT));
                futureTask.get();

                sendMsg2Server(socketChannel);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socketChannel != null) {
                        socketChannel.close();
                    }
                    if (channelGroup != null) {
                        channelGroup.shutdownNow();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sendMsg2Server(AsynchronousSocketChannel socketChannel) throws ExecutionException, InterruptedException {
        for (int i = 0; i < MSG_LOOPS; i++) {
            String msg = generateMsg();
            Future<Integer> writeTask = socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            int writtenBytes = writeTask.get();
            System.out.println(writtenBytes);
        }
    }

    private static String generateMsg() {
        return "消息来自 client, message-id:" + msgCount.addAndGet(1)+"\n";
    }
}
