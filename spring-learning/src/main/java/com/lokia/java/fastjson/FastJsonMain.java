package com.lokia.java.fastjson;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gushu
 * @data 2018/7/26
 */
public class FastJsonMain {

    public static void main(String[] args) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fast-json-bean.json");
        try {
            FastJsonBean fastJsonBean = JSON.parseObject(inputStream,FastJsonBean.class);
            System.out.print(fastJsonBean.getAge()+" "+fastJsonBean);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
