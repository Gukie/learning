package com.lokia.soket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gushu
 * @data 2018/8/1
 */
public class NioServer {

    private static AtomicInteger count = new AtomicInteger(1);

    public static void main(String[] args) throws IOException {
//        java.net.ServerSocket serverSocket = new java.net.ServerSocket();
//        SocketAddress address = new InetSocketAddress("localhost",8990);
//        serverSocket.bind(address);
//        Socket socket = serverSocket.accept();
//        socket.getChannel()

        // 1. Selector open
        // 2. create a Socket channel
        // 3. register the Socket Channel into Selector
        // 4. Selector.select() to wait until there is Channel ready for I/O
        // 5. do action according to the state.

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
        serverSocketChannel.bind(socketAddress);
        serverSocketChannel.configureBlocking(false);

        int validOperations = serverSocketChannel.validOps();
        serverSocketChannel.register(selector,validOperations);

        while(true){

            selector.select();

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                // ready to accept new Connection
                if(selectionKey.isAcceptable()){

                    SocketChannel newConn = serverSocketChannel.accept();
                    newConn.configureBlocking(false);
                    newConn.register(selector,SelectionKey.OP_READ);
                    iterator.remove();

                }else if(selectionKey.isReadable()){
                    SocketChannel connection = (SocketChannel) selectionKey.channel();
                    ByteBuffer msgBuf = ByteBuffer.allocate(4);
                    int byteCount = connection.read(msgBuf);
//                    StringBuilder msg = new StringBuilder();
                    while(byteCount> 0){

//                        byte[] bytes = msgBuf.array();
                        System.out.println(new String(msgBuf.array()));
                        byteCount = connection.read(msgBuf);
                    }

                }else if(selectionKey.isWritable()){
                    System.out.println("ready for writing...");
//                    SocketChannel currChannel = (SocketChannel) selectionKey.channel();
//                    ByteBuffer msgBuf = ByteBuffer.wrap(("server send:"+count.addAndGet(1)+"").getBytes("utf-8"));
//                    currChannel.write(msgBuf);
                }
//                iterator.remove();
            }
        }

    }
}
