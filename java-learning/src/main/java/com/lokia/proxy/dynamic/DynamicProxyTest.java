package com.lokia.proxy.dynamic;

import com.lokia.proxy.dynamic.handler.LogHandler;
import com.lokia.proxy.dynamic.impl.DbServiceImpl;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    public static void main(String[] args) {


        DbService dbService = new DbServiceImpl();
        LogHandler proxyHandler = new LogHandler(dbService);
        DbService dbServiceProxy = (DbService) Proxy.newProxyInstance(dbService.getClass().getClassLoader(),dbService.getClass().getInterfaces(),proxyHandler);
        dbServiceProxy.add(100);
        int totalCount = dbServiceProxy.count();

        System.out.println(totalCount);
    }
}
