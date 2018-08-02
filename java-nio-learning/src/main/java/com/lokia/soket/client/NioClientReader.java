package com.lokia.soket.client;

import com.lokia.soket.NioClient;
import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author gushu
 * @data 2018/8/2
 */
@Data
public class NioClientReader implements Runnable {


    private SocketChannel channel;

    public NioClientReader(SocketChannel channel) {
        setChannel(channel);
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(3);
        CharBuffer charBuffer = CharBuffer.allocate(3);
        CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
        while (true) {
            buffer.clear();

            int readBytes = readByte(buffer);
            while (readBytes > 0) {
                charBuffer.clear();

                buffer.flip();
                decoder.decode(buffer, charBuffer, true);
                int remaining = buffer.remaining();
                byte[] remaingBytes = null;
                if (remaining > 0) {
                    remaingBytes = new byte[remaining];
                    buffer.get(remaingBytes);
                }
                charBuffer.flip();
                System.out.print(charBuffer.toString());

                charBuffer.clear();
                buffer.clear();
                if (remaingBytes != null) {
                    buffer.put(remaingBytes);
                }
                readBytes = readByte(buffer);
            }
            System.out.println();
        }
    }

    private int readByte(ByteBuffer buffer) {
        try {
            return channel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
