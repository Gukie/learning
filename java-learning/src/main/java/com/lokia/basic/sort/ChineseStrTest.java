package com.lokia.basic.sort;

import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gushu
 * @data 2018/8/31
 */
public class ChineseStrTest {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = ChineseStrTest.class.getClassLoader().getResourceAsStream("chinese_char.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> chineseStrList = new ArrayList<>();

        String line = reader.readLine();
        chineseStrList.add(line);
        while((line = reader.readLine())!=null){
            chineseStrList.add(line);
        }
        Collections.sort(chineseStrList,new ChineseComparator());
        chineseStrList.forEach(item->System.out.println(item));

    }
}
