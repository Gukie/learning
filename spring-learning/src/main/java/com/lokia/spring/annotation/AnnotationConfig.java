package com.lokia.spring.annotation;

import com.lokia.spring.annotation.custom.CustomBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author gushu
 * @data 2018/7/23
 */
@Configuration
@ComponentScan(value = "com.lokia.spring.annotation", excludeFilters = {@ComponentScan.Filter(Service.class)},nameGenerator = CustomBeanNameGenerator.class)
public class AnnotationConfig {
}
