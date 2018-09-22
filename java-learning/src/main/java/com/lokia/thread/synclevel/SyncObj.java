package com.lokia.thread.synclevel;

import com.lokia.ThreadUtils;

public class SyncObj {


    private String name;

    public SyncObj(String name){
        this.name = name;
    }

    public void syncOnInstance(){

        synchronized (this){
            while(ThreadUtils.count.get()<=5){
                System.out.println("current-instance: "+name);
                ThreadUtils.count.addAndGet(1);
            }
            // 需要重置为初始值，否则一直是 5
            ThreadUtils.count.set(0);
        }

    }

    public void syncOnClass(){
        synchronized (SyncObj.class){
            while(ThreadUtils.count.get()<=5){
                System.out.println("current-class: "+name);
                ThreadUtils.count.addAndGet(1);
            }
            // 需要重置为初始值，否则一直是 5
            ThreadUtils.count.set(0);

        }
    }
}
