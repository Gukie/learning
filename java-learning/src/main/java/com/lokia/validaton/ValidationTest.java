package com.lokia.validaton;

/**
 * 通过生命@NotNull，然后在某个地方进行统一的校验, 单这也太弱了吧...
 * refer: https://www.jianshu.com/p/a997d03d0cd6
 */
public class ValidationTest {


    public static void main(String[] args) {
        ValidationBean validationBean = new ValidationBean();
        System.out.println(validationBean.getName());

        validationBean.testNotNull(null);
    }
}
