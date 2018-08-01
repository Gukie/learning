package com.lokia.soket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author gushu
 * @data 2018/8/1
 */
public class NioClient {

    private static  String baseMsg = " 客户端 打招呼 ";

    public static void main(String[] args) throws IOException, InterruptedException {

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
        SocketChannel channel = SocketChannel.open(socketAddress);

//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        for(int i = 0;i<5;i++){
//            channel.read(byteBuffer);
//            String receivedMsg = new String(byteBuffer.array());
//            System.out.print(receivedMsg);
//            byteBuffer.clear();

            String msg = generateMsg(i);
            channel.write(ByteBuffer.wrap(msg.getBytes("utf-8")));
            Thread.sleep(50);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static String generateMsg(int currentTime) {
        StringBuilder result = new StringBuilder();

        int loop = currentTime+1;
        for(int i = 0;i< loop;i++){
            result.append(baseMsg);
        }

        return result.toString();

    }

}
