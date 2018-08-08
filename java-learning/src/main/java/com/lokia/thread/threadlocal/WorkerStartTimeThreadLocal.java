package com.lokia.thread.threadlocal;

/**
 * 存储线程执行的时候的时间
 * @author gushu
 * @data 2018/8/8
 */
public class WorkerStartTimeThreadLocal {

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };


    public Long get(){
        return startTimeThreadLocal.get();
    }

    public void remove(){
        startTimeThreadLocal.remove();
    }
}
