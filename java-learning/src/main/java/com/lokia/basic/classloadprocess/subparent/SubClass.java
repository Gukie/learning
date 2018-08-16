package com.lokia.basic.classloadprocess.subparent;


import com.lokia.basic.classloadprocess.PrintOut;

/**
 * @author gushu
 * @data 2018/8/16
 */
public class SubClass extends ParentClass{

//    String message = PrintOut.pringOUt("sub-class");
   static String message = PrintOut.pringOUt("static - sub-class - message");
   static {
       System.out.print("static - sub-class - block");
   }


   int age = 12;
   String name = PrintOut.pringOUt("name") ;
   String desc = PrintOut.pringOUt("desc") ;
    {
        System.out.println("instance-block in sub class");
        System.out.println("age："+age);
    }

   public SubClass(){
       System.out.println(" first code line in sub-class-constructor...");
   }


}
