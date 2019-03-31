package com.lokia.thread.uncaughtexception;

import java.io.IOException;
import java.util.Optional;

/**
 * @author gushu
 * @data 2018/8/11
 */
public class UncaughtExceptionTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadTest(),"uncaught-exception-thread");
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("uncaught exception*****: ");
            e.printStackTrace(System.out);

            System.out.println(t.getName()+" is still alive or not:"+t.isAlive());
            try {
                t.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        thread.start();


        try {
            Thread.sleep(23);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(thread.isAlive()){
            System.out.println(thread.getName()+" is still alive");
        }else{
            System.out.println(thread.getName()+" is dead");
        }


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
