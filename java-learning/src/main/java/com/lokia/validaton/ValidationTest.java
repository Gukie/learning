package com.lokia.validaton;

public class ValidationTest {

    public static void main(String[] args) {
        ValidationBean validationBean = new ValidationBean();
        System.out.println(validationBean.getName());

        validationBean.testNotNull(null);
    }
}
