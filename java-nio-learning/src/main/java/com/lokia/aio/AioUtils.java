package com.lokia.aio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AioUtils {

    public final static int PORT = 6666;
    public final static String HOST = "127.0.0.1";

    public final static int CLIENT_CHANNEL_NUM = 80;
    public static CountDownLatch countDownLatch = new CountDownLatch(CLIENT_CHANNEL_NUM);

}
