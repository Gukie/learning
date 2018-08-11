package com.lokia.thread.uncaughtexception;

/**
 * @author gushu
 * @data 2018/8/11
 */
public class ThreadTest implements  Runnable {
    @Override
    public void run() {
        throw new RuntimeException("hahahahhd********");
    }
}
