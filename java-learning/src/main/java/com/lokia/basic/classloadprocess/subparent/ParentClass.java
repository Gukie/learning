package com.lokia.basic.classloadprocess.subparent;

import com.lokia.basic.classloadprocess.PrintOut;
import com.lokia.basic.inherit.Parent;

/**
 * @author gushu
 * @data 2018/8/16
 */
public class ParentClass {

   static String message = PrintOut.pringOUt("static - parent-class - message");
   static {
       System.out.println("static - parent-class - block");
   }

   ParentClass(){
       System.out.println(" first code line in parent-class-constructor...");
   }
}
