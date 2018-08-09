package com.lokia.aio.thread;

import com.lokia.IoUtils;
import com.lokia.aio.AioUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioWriteTask implements Runnable {

    private String msg;
    private AsynchronousSocketChannel socketChannel;

    public AioWriteTask(String msg,AsynchronousSocketChannel socketChannel) {
        setMsg(msg);
        setSocketChannel(socketChannel);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AsynchronousSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {

        try {
            AioUtils.countDownLatch.await();
            sendMsg2Server(socketChannel);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
//            try {
////                if (socketChannel != null) {
////                    socketChannel.close();
////                }
////                if (channelGroup != null) {
////                    channelGroup.shutdownNow();
////                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }


    private  void sendMsg2Server(AsynchronousSocketChannel socketChannel) throws ExecutionException, InterruptedException {
       String message = wrapMsg();
        Future<Integer> writeTask = null;
        while(writeTask == null){
            try{
                // 由于AsynchronousSocketChannel的读与写，是线程安全的，多个线程同时写或者读的时候，都必须等上一个read或者write完成之后再开始，否则会报java.nio.channels.WritePendingException错误，这时候就需要等待了
                writeTask = socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            }catch (java.nio.channels.WritePendingException e){
                System.out.println(Thread.currentThread().getName()+"-"+e);
//                e.printStackTrace();
            }
        }
        int writtenBytes = writeTask.get();
        System.out.println(writtenBytes+":"+message);
    }

    private String wrapMsg() {
        StringBuilder result = new StringBuilder();
        result.append(Thread.currentThread().getName());
        result.append("- ");
        result.append(msg).append("\n");
        return result.toString();

    }

}
