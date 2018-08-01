package com.lokia.java;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @author gushu
 * @data 2018/7/20
 */
public class JavaBasicTestMain {

    public static void main(String[] args) {

        test();
        getExtDirs();
    }

    private static void test(String ... args){
        System.out.println("test...");
    }

    private static void test1(String[] args){
        System.out.println("test...");
    }

    private static File[] getExtDirs() {
        String var0 = System.getProperty("java.ext.dirs");
        File[] var1;
        if (var0 != null) {
            StringTokenizer var2 = new StringTokenizer(var0, File.pathSeparator);
            int var3 = var2.countTokens();
            var1 = new File[var3];

            for(int var4 = 0; var4 < var3; ++var4) {
                var1[var4] = new File(var2.nextToken());
            }
        } else {
            var1 = new File[0];
        }

        return var1;
    }
}
