package com.lokia.io.chars;


import com.lokia.IoUtils;

public class IoWriterTest {


    public static void main(String[] args) {

        for(int i =0;i<8;i++){
            Thread thread = new Thread(new IOWriteThread(),"thread-"+(i+1));
            thread.start();
        }
        try {
            IoUtils.COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished...");

    }
}
