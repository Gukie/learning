package com.lokia.validaton;

import javax.validation.constraints.NotNull;

public class ValidationBean {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void testNotNull(@NotNull String test){
        System.out.println(test);
    }
}
