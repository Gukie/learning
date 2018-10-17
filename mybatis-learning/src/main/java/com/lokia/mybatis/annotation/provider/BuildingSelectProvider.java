package com.lokia.mybatis.annotation.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BuildingSelectProvider {

    /**
     * refer: http://www.mybatis.org/mybatis-3/java-api.html#directoryStructure
     * @return
     */
    public String getByName(@Param("orderBy") String orderBy){

        /**
         * 以下写法是一种反范式的写法，容易造成内存泄漏, 不建议这么写
         * refer:
         * - http://www.mybatis.org/mybatis-3/java-api.html#directoryStructure
         * - https://stackoverflow.com/questions/1958636/what-is-double-brace-initialization-in-java
         * - https://blog.jooq.org/2014/12/08/dont-be-clever-the-double-curly-braces-anti-pattern/
         *
         * 创建一个SQL的内部类，然后实例化对应的内部类
         */
        return new SQL(){
            {
              SELECT("*");
              FROM("building");
              WHERE("name like concat('%',#{name},'%')");
              ORDER_BY(orderBy);

            }
        }.toString();


    }
}
