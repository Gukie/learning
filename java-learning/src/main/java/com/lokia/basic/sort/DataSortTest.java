package com.lokia.basic.sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gushu
 * @data 2018/8/31
 */
public class DataSortTest {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<SortBean> sortBeanList = new ArrayList<>();
        SortBean sortBean1 = new SortBean();
        sortBean1.setBirthday(simpleDateFormat.parse("2018-08-21 00:00:00"));
        sortBean1.setName("A");
        SortBean sortBean2 = new SortBean();
        sortBean2.setBirthday(simpleDateFormat.parse("2018-08-22 00:00:00"));
        sortBean2.setName("B");
        SortBean sortBean3 = new SortBean();
        sortBean3.setBirthday(simpleDateFormat.parse("2018-08-23 00:00:00"));
        sortBean3.setName("Dog");
        SortBean sortBean4 = new SortBean();
        sortBean4.setBirthday(simpleDateFormat.parse("2018-08-23 00:00:00"));
        sortBean4.setName("Cat");
        SortBean sortBean5 = new SortBean();
        sortBean5.setBirthday(simpleDateFormat.parse("2018-08-23 00:00:00"));
        sortBean5.setName("Can");


        sortBeanList.add(sortBean1);
        sortBeanList.add(sortBean2);
        sortBeanList.add(sortBean3);
        sortBeanList.add(sortBean4);
        sortBeanList.add(sortBean5);

        Collections.sort(sortBeanList);
        sortBeanList.forEach(item ->System.err.println(item));
    }
}

