package com.lokia.dubbo.service.impl;

import com.lokia.service.UserService;

public class SpringUserServiceImpl implements UserService {
    public String getName() {
        return "hello, this is from spring";
    }
}
