package com.lokia.thread.threadpool;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建文件，然后写点信息
 * @author gushu
 * @data 2018/8/8
 */
public class FileCreateAndMsgWriteTask implements Callable<Void> {


    private String dir = "D:\\tmp\\thread-pool";
    private String filePrefix = "threadpool-";
    private Random random = new Random();
    private AtomicInteger fileNum = new AtomicInteger(1);

    @Override
    public Void call() {
        int loops = (int) (random.nextDouble()* 10000);
        System.out.println("loop:"+loops);
        // open dir
        openFileChannel(dir);
        for(int i = 0;i < loops ;i++){

            int currentFileNo = fileNum.addAndGet(1);
            String fileName = filePrefix + currentFileNo;
            FileChannel fileChannel = openFileChannel(dir,fileName);


            String currentMsg = generateMsg(currentFileNo);
            try {
                ByteBuffer buffer = ByteBuffer.wrap(currentMsg.getBytes());
                fileChannel.write(buffer);
//                fileChannel.force(true);
//                fileChannel.truncate()
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fileChannel != null){
                    try {
                        fileChannel.force(true);
                        fileChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

    private FileChannel  openFileChannel(String first, String ... others) {
        Path fileDir = Paths.get(first,others);
        try {
            return FileChannel.open(fileDir, EnumSet.of(StandardOpenOption.CREATE,StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.APPEND));
//            return FileChannel.open(fileDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateMsg(int currentFileNo) {
        return "你好，消息来自 北极, fileNo: "+currentFileNo;
    }
}
