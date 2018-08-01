package com.lokia.spring.webmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 这里主要负责配置：
 *
 * 1. 当前servlet 请求, 如何转发 - HandlerMapping (RequestMappingHandlerMapping)
 * 2. 发生Exception，如何处理 -  HandlerExceptionResolver
 * 3. 数据之间的转换 convert - 可以通过 messageConverter 进行
 *
 * 以上可以通过 WebMvcConfigurationSupport 实现
 *
 * @author gushu
 * @data 2018/7/26
 */
@Configuration
public class DispatcherServletConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
//        super.configureViewResolvers(registry);
//        registry.getViewResolvers().add(new InternalResourceViewResolver());

//        registry.

        InternalResourceViewResolver customViewResolver = new InternalResourceViewResolver();
        customViewResolver.setPrefix("WEB-INF/");
        customViewResolver.setSuffix(".html");
        registry.viewResolver(customViewResolver);
    }
}
