package com.lokia.java8;

import java.util.Arrays;
import java.util.List;

/**
 * @author gushu
 * @data 2018/8/25
 */
public class StreamForeachTerminateTest {

    public static void main(String[] args) {
        List<String> itemList = Arrays.asList("aaa", "bbb", "ddd", "cccc");

        itemList.forEach(item -> {
            if (item.equals("bbb")) { // will continue to handle other item. stream does not have break.
                return;
            }
            System.out.println(item);
        });

        System.out.println("end...");

    }
}
