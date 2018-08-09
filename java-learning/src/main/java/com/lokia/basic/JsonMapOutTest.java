package com.lokia.basic;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gushu
 * @data 2018/8/9
 */
public class JsonMapOutTest {

    public static void main(String[] args) {
        Map<Long,String > data = new HashMap<>(16);
        data.put(2L,"hello");
        data.put(3L,"moon");

        String result = JSON.toJSONString(data);
        System.out.println(result);


        Map<Long,String> other = JSON.parseObject(result,Map.class);
        for(Map.Entry<Long,String> item: other.entrySet()){
            System.out.println(item.getKey()+":"+item.getValue());
        }
    }
}
