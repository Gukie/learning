package com.lokia.soket;

import java.io.IOException;
import java.net.InetSocketAddress;
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

    public static void main(String[] args) throws IOException, InterruptedException {
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
        SelectionKey registeredSK = serverSocketChannel.register(selector, validOperations);
//        serverSocketChannel
//        registeredSK.interestOps(SelectionKey.OP_WRITE);
//        SelectionKey registeredSK = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT|SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        printSelectionkey("registered selection key:", registeredSK);


        // decoder
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();

        int loop = 0;
        while (true) {
            loop++;
            System.out.println();
            System.out.println("*********************** start Selector.select() selecting..."+loop);
            // 4. Selector.select() to wait until there is Channel ready for I/O
            selector.select();

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            if (selectionKeySet == null || selectionKeySet.isEmpty()) {
                System.out.println("***** selectionKeySet is empty...");
            }
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // ready to accept new Connection
                if (selectionKey.isAcceptable()) {
                    printSelectionkey("Accepting...",selectionKey);
                    SocketChannel newConn = serverSocketChannel.accept();
                    if (newConn == null) {
                        continue;
                    }
                    newConn.configureBlocking(false);
//        SelectionKey newSK = newConn.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
//                    SelectionKey newSK = newConn.register(selector, SelectionKey.OP_WRITE);
                    SelectionKey newSK = newConn.register(selector, SelectionKey.OP_READ);
                    printSelectionkey("new connection registered: ", newSK);
                }

                if (selectionKey.isReadable()) {
                    printSelectionkey("Reading...",selectionKey);
                    try{
                        readMessageFromChannel(decoder, selectionKey);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (selectionKey.isWritable()) {
                    printSelectionkey("Writing...",selectionKey);
                    SocketChannel currChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer msgBuf = ByteBuffer.wrap(("server send: 你好 " + count.addAndGet(1) + "\n").getBytes("utf-8"));
                    currChannel.write(msgBuf);
                    Thread.sleep(count.get() * 300);
//                    iterator.remove();
                }
                iterator.remove();
            }


        }

    }

    private static void readMessageFromChannel(CharsetDecoder decoder, SelectionKey selectionKey) throws IOException {
        SocketChannel connection = (SocketChannel) selectionKey.channel();
        if(connection == null){
           printSelectionkey("connection is closed while reading ",selectionKey);
           return;
        }

        int bufferCapacity = 3;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferCapacity);
        CharBuffer charBuffer = CharBuffer.allocate(bufferCapacity);

        int readNum = connection.read(byteBuffer);
//                    StringBuilder msg = new StringBuilder();
        while (readNum > 0) {
//                        System.out.println("************ read start...");
//                        byte[] bytes = byteBuffer.array();
            byteBuffer.flip();
//                        decoder.decode(byteBuffer,charBuffer,readNum<bufferCapacity);// 这时候，msgBuf的position会被设置为： msgBuf的原来的position+能被decode到的byte的长度(可能有的byge不能够decode，此时 msgBuf的position就不会等于msgBuf的limit)
            decoder.decode(byteBuffer, charBuffer, false);// 这时候，msgBuf的position会被设置为： msgBuf的原来的position+能被decode到的byte的长度(可能有的byge不能够decode，此时 msgBuf的position就不会等于msgBuf的limit)
            charBuffer.flip();
            System.out.print(charBuffer.toString());

            // 将没有解码的数据，存起来
            int notReadCount = byteBuffer.remaining();
            byte[] notReadByte = null;
            if (notReadCount > 0) {
                notReadByte = new byte[notReadCount];
                byteBuffer.get(notReadByte);
            }

            byteBuffer.clear();
            charBuffer.clear();

            //如果有多余的字节没有被处理，应该放进来，以便下一次被处理
            if (notReadByte != null) {
                byteBuffer.put(notReadByte);
            }
            readNum = connection.read(byteBuffer);
//                        System.out.println("************ read end...");
        }
        System.out.println();
    }


    private static void printSelectionkey(String mywords, SelectionKey selectionKey) {
        StringBuilder rseutl = new StringBuilder();
        rseutl.append("id: " + selectionKey);
        rseutl.append(" ;interest ops-" + selectionKey.interestOps());
        rseutl.append(" ;read ops-" + selectionKey.readyOps());
        rseutl.append(" ;isWritable-" + selectionKey.isWritable());
        rseutl.append(" ;isReadable-" + selectionKey.isReadable());
        rseutl.append(" ;isAcceptable-" + selectionKey.isAcceptable());
        rseutl.append(" ;isConnectable-" + selectionKey.isConnectable());
        rseutl.append(" ;isValid-" + selectionKey.isValid());

//        System.out.println();
//        System.out.println();
        System.out.println(mywords + " " + rseutl.toString());
//        System.out.println();
    }
}
