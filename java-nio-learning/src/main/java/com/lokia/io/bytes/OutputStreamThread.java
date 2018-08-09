package com.lokia.io.bytes;

import com.lokia.IoUtils;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author gushu
 * @data 2018/8/3
 */

public class OutputStreamThread implements Runnable {

    private CountDownLatch countDownLatch;
    public OutputStreamThread(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        String fileDir = IoUtils.getIoFileDir();
        File file = IoUtils.getFile(fileDir, IoUtils.IO_FILE_NAME);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file,true);

            for (int i = 0; i < 5; i++) {
                String message = generateMsg(i);
                // 直接调用的是底层OS的write方法，是线程安全的，所以在实现的时候可以不用锁
                fileOutputStream.write(message.getBytes("UTF-8"));
            }
            fileOutputStream.write("\n".getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream !=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
    }

    private static String generateMsg(int currentLoop) {

        StringBuilder result = new StringBuilder();
        String threadname = Thread.currentThread().getName();
        result.append("你好, this is from "+ threadname+", you are the "+currentLoop+" one");

        int index = (int) (Math.random() * 1000) % IoUtils.OUT_MSG_LST.length;
        return result.append(IoUtils.OUT_MSG_LST[index]).append("\n").toString();
    }
}