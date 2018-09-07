package com.lokia.basic;

/**
 * @author gushu
 * @data 2018/8/2
 */
public class BitwiseTest {

    public static void main(String[] args) {

        System.out.println("10: "+Integer.toBinaryString(10));
        System.out.println("10>>2: "+(10>>2)); // 除于 2的2次方
        System.out.println("10>>>2: "+(10>>>2));
        System.out.println(Integer.toBinaryString(10>>2));
        System.out.println(Integer.toBinaryString(10>>>2));

        System.out.println();
        System.out.println("-15: "+Integer.toBinaryString(-15));
        System.out.println("-15>>2: "+(-15>>2));
        System.out.println("-15>>>2: "+( -15 >>> 1) ); // 高位会补零，得到的可能会是一个正数
        System.out.println(Integer.toBinaryString(-15>>2));
        System.out.println(Integer.toBinaryString(-15>>>1));

        System.out.println();
        System.out.println(Integer.toBinaryString(4));
        System.out.println("4<<2: "+(4<<2)); // 乘以2的2次方
        System.out.println("-4<<2: "+(-4<<2));
        System.out.println(Integer.toBinaryString(4<<2));
        System.out.println(Integer.toBinaryString(-4<<2));
    }



}
