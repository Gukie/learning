package com.lokia;

import java.text.DecimalFormat;

/**
 * @author gushu
 * @data 2018/8/24
 */
public class NumberFormatUtils {
    private static DecimalFormat format = new DecimalFormat();

    /**
     * 转换为字符串
     *
     * @param data
     * @param reservedDecimalCount 保留小数点后几位, 小于1表示不保留
     * @return
     */
    public static String format(Number data, int reservedDecimalCount) {

        String pattern = getPattern(reservedDecimalCount);
        format.applyPattern(pattern);
        return format.format(data);
    }

    private static String getPattern(int reservedDecimalCount) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#");
        if (reservedDecimalCount >= 1) {
            pattern.append(".");
            for (int i = 0; i < reservedDecimalCount; i++) {
                pattern.append("0");
            }
        }
        return pattern.toString();
    }

    public static void main(String[] args) {

        String formattedStr = format(12,3);
        System.out.println(formattedStr);

        formattedStr = format(12.3356,3);
        System.out.println(formattedStr);
        formattedStr = format(1112.356,3);
        System.out.println(formattedStr);

        System.out.println("************");
        formattedStr = format(12.3356,0);
        System.out.println(formattedStr);
        formattedStr = format(12.3356,1);
        System.out.println(formattedStr);
        formattedStr = format(12.356,2);
        System.out.println(formattedStr);
    }
}
