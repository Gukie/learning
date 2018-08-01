package com.lokia.spring.autowire;

import org.springframework.stereotype.Component;

/**
 * @author gushu
 * @data 2018/7/20
 */
@Component
public class AutowireService {

//    @Autowired(required = false)
    private AutowireTestBean autowireTestBean;
    public AutowireTestBean getAutowireTestBean() {
        return autowireTestBean;
    }

//    @Required
    public void setAutowireTestBean(AutowireTestBean autowireTestBean) {
        this.autowireTestBean = autowireTestBean;
    }
}