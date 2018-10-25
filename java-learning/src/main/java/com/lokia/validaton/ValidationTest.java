package com.lokia.validaton;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 通过生命@NotNull，然后在某个地方进行统一的校验, 单这也太弱了吧...
 * refer: https://www.jianshu.com/p/a997d03d0cd6
 */
public class ValidationTest {


    public static void main(String[] args) {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        ValidationBean validationBean = new ValidationBean();
        Set<ConstraintViolation<ValidationBean>> validateResult =  validator.validate(validationBean);
        validateResult.forEach(item-> System.out.println(item.getMessage()));
        System.out.println("***********"+validationBean.getName());

        validationBean.testNotNull(null);
    }
}
