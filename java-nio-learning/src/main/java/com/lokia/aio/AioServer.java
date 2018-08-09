package com.lokia.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.*;

public class AioServer {


    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        AsynchronousChannelGroup channelGroup = null;
        AsynchronousServerSocketChannel serverSocketChannel = null;

        try {
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPool, 30);
            serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);

            SocketAddress address = new InetSocketAddress(AioUtils.HOST, AioUtils.PORT);
            int maxConnection = 30;
            serverSocketChannel.bind(address, maxConnection);

            doHandleConnection(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
                if (channelGroup != null) {
                    channelGroup.shutdownNow();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private static void doHandleConnection(AsynchronousServerSocketChannel serverSocketChannel) throws InterruptedException, ExecutionException {

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        ByteBuffer data = ByteBuffer.allocate(17);
        CharBuffer charBuffer = CharBuffer.allocate(17);
        while (true) {
            data.clear();
            charBuffer.clear();

            data.put(("\""+Thread.currentThread().getName()+"\"").getBytes());
            Future<AsynchronousSocketChannel> futureTask = serverSocketChannel.accept();
            AsynchronousSocketChannel socketChannel = futureTask.get();
//            synchronized (socketChannel){
//                ByteBuffer data = ByteBuffer.allocate(5);
//                CharBuffer charBuffer = CharBuffer.allocate(5);
                if (socketChannel != null && socketChannel.isOpen()) {
                    Future<Integer> readNum = socketChannel.read(data);

                    while (readNum.get() > 0) {
                        data.flip();
                        decoder.decode(data, charBuffer, false);
                        int remaining = data.remaining();
                        byte[] remainingBytes = null;
                        if (remaining > 0) {
                            remainingBytes = new byte[remaining];
                            data.get(remainingBytes);
                        }
                        charBuffer.flip();
                        System.out.print(charBuffer.toString());
                        data.clear();
                        charBuffer.clear();

                        if (remainingBytes != null) {
                            data.put(remainingBytes);
                        }
                        readNum = socketChannel.read(data);
                    }
                }
//            }
        }
    }
}
