package com.lokia;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gushu
 * @data 2018/8/16
 */
public class DateTimeUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static  String getFormattedStr(Date date){
        return format.format(date);
    }
}
