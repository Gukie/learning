package com.lokia.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/24
 */
@Component
public abstract class BlackListEventPublisher {

    // 其实，它就是 ApplicationContext
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishBlackItem(String blackItem){
        eventPublisher.publishEvent(getBlackListEvent(blackItem+"-"+System.currentTimeMillis()));
    }

    @Lookup
    public abstract BlackListEvent getBlackListEvent(String blackItem);
}
