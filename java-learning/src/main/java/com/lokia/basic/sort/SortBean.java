package com.lokia.basic.sort;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gushu
 * @data 2018/8/31
 */
@Data
public class SortBean implements Comparable<SortBean>{

    private String name;
    private Date birthday;

    @Override
    public int compareTo(SortBean other) {
        if(other == null){
            return 1;
        }
        int birthdayCompareVal = birthdayCompareVal(other);
        if(birthdayCompareVal != 0){
            return birthdayCompareVal;
        }
        return nameCompareVal(other);
    }

    private int nameCompareVal(SortBean other) {
        return this.getName().compareTo(other.getName());
    }

    private int birthdayCompareVal(SortBean other) {
        if(this.birthday.getTime() > other.getBirthday().getTime()){
            return 1;
        }
        if (this.birthday.getTime() < other.getBirthday().getTime()) {
            return -1;
        }
        return 0;
    }

    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "birthday:"+simpleDateFormat.format(birthday)+",name:"+name;
    }
}
