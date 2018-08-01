package com.lokia.soket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
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



        // decoder
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();


        while(true){
            // 4. Selector.select() to wait until there is Channel ready for I/O
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
                    System.out.println("ready for read...");
                    SocketChannel connection = (SocketChannel) selectionKey.channel();

                    int bufferCapacity = 3;
                    ByteBuffer byteBuffer = ByteBuffer.allocate(bufferCapacity);
                    CharBuffer charBuffer = CharBuffer.allocate(bufferCapacity);

                    int readNum = connection.read(byteBuffer);
//                    StringBuilder msg = new StringBuilder();
                    while(readNum> 0){
//                        System.out.println("************ read start...");
//                        byte[] bytes = byteBuffer.array();
                        byteBuffer.flip();
//                        decoder.decode(byteBuffer,charBuffer,readNum<bufferCapacity);// 这时候，msgBuf的position会被设置为： msgBuf的原来的position+能被decode到的byte的长度(可能有的byge不能够decode，此时 msgBuf的position就不会等于msgBuf的limit)
                        decoder.decode(byteBuffer,charBuffer,true);// 这时候，msgBuf的position会被设置为： msgBuf的原来的position+能被decode到的byte的长度(可能有的byge不能够decode，此时 msgBuf的position就不会等于msgBuf的limit)
                        charBuffer.flip();
                        System.out.print(charBuffer.toString());

                        // 将没有解码的数据，存起来
                        int notReadCount = byteBuffer.remaining();
                        byte[] notReadByte = null;
                        if(notReadCount>0){
                            notReadByte = new byte[notReadCount];
                            byteBuffer.get(notReadByte);
                        }

                        byteBuffer.clear();
                        charBuffer.clear();

                        //如果有多余的字节没有被处理，应该放进来，以便下一次被处理
                        if(notReadByte!=null){
                            byteBuffer.put(notReadByte);
                        }
                        readNum = connection.read(byteBuffer);
//                        System.out.println("************ read end...");
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
