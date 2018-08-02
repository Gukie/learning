package com.lokia.soket.client;

import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author gushu
 * @data 2018/8/2
 */
@Data
public class NioClientWriter implements Runnable {

    private final  static  String baseMsg = "来自客户端的问候 ";
    private  SocketChannel channel;
    private int writeLoops ;

    public NioClientWriter(SocketChannel channel, int writeLoops) {
        setChannel(channel);
        setWriteLoops(writeLoops);
    }

    @Override
    public void run() {

        for (int i = 0; i < writeLoops; i++) {
            String msg = generateMsg(i);
            try {
                channel.write(ByteBuffer.wrap(msg.getBytes("utf-8")));
                Thread.sleep((i+1)*550);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private String generateMsg(int currentLoop) {
        StringBuilder result = new StringBuilder();

        int loop = currentLoop+1;
        for(int i = 0;i< loop;i++){
            result.append(baseMsg).append(i+1);
        }
        if( currentLoop ==  writeLoops-1){
            result.append(" 会话结束...");
        }
        result.append("\n");

        return result.toString();

    }
}
