package com.lokia.basic.classloadprocess;

import com.lokia.basic.classloadprocess.subparent.SubClass;

/**
 * @author gushu
 * @data 2018/8/16
 */
public class ClassInitializeSuperTest {

    static class StaticSubClass extends StaticParentClass {
        static String message = PrintOut.pringOUt("static-sub-class");
    }

    static class StaticParentClass {
        static String message = PrintOut.pringOUt("static-parent-class");
    }

    public static void main(String[] args) {
        //1. directly invoke the static field, to trigger initialize phase
        String message = StaticSubClass.message;

        //2. test with Class#forName method to invoke initialize phase.
        testByClassForName();

        //3. test with new to construct instance.
        testNewInstance();

    }

    private static void testNewInstance() {
        System.out.println();
        System.out.println("******** 实例化过程....");
        SubClass subClass = new SubClass();

    }

    private static void testByClassForName() {
        System.out.println();
        System.out.println("******** test Class.forName , 它值是调用 static部分，先父类后子类，先static-block，后static-fields");
        try {
            String subClassName = "com.lokia.basic.classloadprocess.subparent.SubClass";
            Class.forName(subClassName);
//            Class.forName(subClassName,false,ClassInitializeSuperTest.class.getClassLoader()); // 这样的化，是不会进行 initialize这阶段的
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
