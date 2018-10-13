package com.lokia.proxy.dynamic.impl;

import com.lokia.proxy.dynamic.DbService;

public class DbServiceImpl implements DbService {

    private Integer totalCount = 0;

    @Override
    public void add(Integer count) {
        totalCount += count;
    }

    @Override
    public int count() {

        return totalCount;
    }
}
