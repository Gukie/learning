package com.lokia.thread.threadpool.task;

import com.lokia.IoUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * uncaughtExceptionHandlerTestTask
 *
 * @author gushu
 * @data 2018/8/8
 */
public class UncaughtExceptionHandlerTestTask implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException(" exception made to test Thread.UncaughtExceptionHandler.");
    }
}
