package com.lokia.thread.threadpool;

import com.lokia.IoUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建文件，然后写点信息
 *
 * @author gushu
 * @data 2018/8/8
 */
public class FileCreateAndMsgWriteTask implements Callable<Void> {


//    private String dir = "D:\\tmp\\thread-pool\\ggg";
    private String filePrefix = "threadpool-";
    private String fileSuffix = ".txt";

    private Random random = new Random();
    private AtomicInteger fileNum = new AtomicInteger(0);

    @Override
    public Void call() {
        int loops = (int) (random.nextDouble() * 1809);
        System.out.println("loop:" + loops);
        String dir = IoUtils.getIoFileDir();
        // open dir
        openFileChannel4Write(dir);
        for (int i = 0; i < loops; i++) {

            int currentFileNo = fileNum.addAndGet(1);
            String fileName = filePrefix + currentFileNo + fileSuffix;
            FileChannel fileChannel = openFileChannel4Write(dir, fileName);


            String currentMsg = generateMsg(currentFileNo);
            try {
                ByteBuffer buffer = ByteBuffer.wrap(currentMsg.getBytes());
                fileChannel.write(buffer);
//                fileChannel.force(true);
//                fileChannel.truncate()
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileChannel != null) {
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


    private FileChannel openFileChannel4Write(String first, String... others) {
        Path fileDir = Paths.get(first, others);

        boolean isDir = IoUtils.isDir(fileDir.toFile());
        if(isDir){
            if (!Files.exists(fileDir)) {
                try {
                    Files.createDirectories(fileDir);
//                else {
//                    Files.createFile(fileDir);
//                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        try {
            // 这里需要 fileDir所在的目录存在, 所以在这之前需要将文件所在的文件夹的目录给创建出来
            Set<StandardOpenOption> openOptions =  EnumSet.of(StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
            FileAttribute fileAttribute = IoUtils.generateFileAttr4Unix();
            if(fileAttribute !=null){
                return FileChannel.open(fileDir, openOptions,fileAttribute);
            }
            return FileChannel.open(fileDir, openOptions);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private boolean isDirectoryFile(Path fileDir) {
//        String fileName = fileDir.toFile().getName();
//        int dotIdx = fileName.indexOf(".");
//        if(dotIdx == -1){
//           return true;
//       }
//        return false;
//    }

    private String generateMsg(int currentFileNo) {
        String threadName = Thread.currentThread().getName();
        return "你好，消息来自: "+threadName+", fileNo: " + currentFileNo+"\n";
    }
}
