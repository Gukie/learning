package com.lokia.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/24
 */
@Component
public class BlackListListener implements ApplicationListener<BlackListEvent> {

    @Override
    public void onApplicationEvent(BlackListEvent event) {
        System.out.println("received black item: "+event.getSource()+", id:"+event.hashCode());
    }
}
