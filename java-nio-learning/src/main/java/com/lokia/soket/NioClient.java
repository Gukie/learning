package com.lokia.soket;

import com.lokia.soket.client.NioClientReader;
import com.lokia.soket.client.NioClientWriter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author gushu
 * @data 2018/8/1
 */
public class NioClient {

    private static int writeLoops = 5;

    public static void main(String[] args) throws IOException, InterruptedException {

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
        SocketChannel channel = SocketChannel.open(socketAddress);

        writeMsg2Server(channel);
//        readMsgFromServer(channel);
        Thread.sleep(60*24*60*1000);
    }

    private static void readMsgFromServer(SocketChannel channel) throws IOException {
        Thread reader = new Thread(new NioClientReader(channel));
        reader.start();
    }

    private static void writeMsg2Server(SocketChannel channel) throws IOException, InterruptedException {
       Thread writer = new Thread(new NioClientWriter(channel,writeLoops));
       writer.start();
    }

}
