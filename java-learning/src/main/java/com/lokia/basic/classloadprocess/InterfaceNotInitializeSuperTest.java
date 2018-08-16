package com.lokia.basic.classloadprocess;

/**
 * @author gushu
 * @data 2018/8/16
 */
public class InterfaceNotInitializeSuperTest {

    interface SubInterafce extends ParentInterface{
        String message = PrintOut.pringOUt("sub");
    }
    interface ParentInterface{
        String message = PrintOut.pringOUt("parent");
    }

    public static void main(String[] args) {
        String message = SubInterafce.message;
//        System.out.println(message);
    }
}
