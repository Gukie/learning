package com.lokia.jvmparam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gushu
 * @data 2018/8/15
 */
public class HeapMonitorTest {

    static int loop = 40;

    static int _1MB = 1024* 1024;

    public static void main(String[] args) throws InterruptedException {

        List<byte[]> byteList = new ArrayList<>();

        for(int i =0;i< loop;i++){
            byteList.add(new byte[_1MB/4]);
            Thread.sleep(1000);
        }

        System.out.println("finished...");

    }

//    class HeapIncreaseRunnable implements  Runnable{
//
//        @Override
//        public void run() {
//            new
//        }
//    }
}
