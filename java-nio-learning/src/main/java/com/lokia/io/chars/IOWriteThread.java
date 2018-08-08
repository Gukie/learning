package com.lokia.io.chars;

import com.lokia.io.IoUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author gushu
 * @data 2018/8/3
 */
public class IOWriteThread implements Runnable {

    @Override
    public void run() {


        File file = IoUtils.getFile(IoUtils.getIoFileDir(), IoUtils.IO_FILE_NAME);
        BufferedWriter bufferedWriter = null;
        OutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            /**
             * 由于写字符, 到最后，都需要转为字节码进行写; 所以一般都需要使用 OutputStreamWriter，将字符转为字节
             */
//            synchronized (IoUtils.WRITER_LOCK){
//                OutputStream fileOutputStream = new FileOutputStream(file,true);
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.forName("utf-8"));
//                bufferedWriter = new BufferedWriter(outputStreamWriter);
//
//                String message = generateMsg();
//                System.out.println(message);
//                bufferedWriter.write(message);
//                bufferedWriter.flush();
//            }

            fileOutputStream = new FileOutputStream(file, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.forName("utf-8"));
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String message = generateMsg();
            System.out.print(message);
            // 这里的write，有几个步骤，涉及到原子操作，在实现的时候用了锁; 但write本身可以不用锁，是互斥的
            bufferedWriter.write(message);
//            bufferedWriter.flush();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // 关闭的顺序跟创建的顺序，是反过来的; 最核心最里面的，要最后关闭
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            IoUtils.COUNT_DOWN_LATCH.countDown();
        }
    }

    private static String generateMsg() {
        String currentThreadName = Thread.currentThread().getName();
        StringBuilder result = new StringBuilder(currentThreadName);

        int index = (int) (Math.random() * 1000) % IoUtils.OUT_MSG_LST.length;
        return result.append(IoUtils.OUT_MSG_LST[index]).append("-").append(System.currentTimeMillis()).append("\n").toString();
    }
}

