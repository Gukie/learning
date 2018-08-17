package com.lokia.mybatis.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author gushu
 * @data 2018/8/17
 */
@Data
public class Building {
    private String id;
    private String name;
    private String location;
    private Date gmtCreated;
}
