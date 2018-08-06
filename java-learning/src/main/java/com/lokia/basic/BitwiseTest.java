package com.lokia.basic;

/**
 * @author gushu
 * @data 2018/8/2
 */
public class BitwiseTest {

    public static void main(String[] args) {
        int a = 8;
        int right = a>>1;
        int left = a<<0;

        System.out.println("after left bitwise:"+left);
        System.out.println("after right bitwise:"+right);
    }
}
