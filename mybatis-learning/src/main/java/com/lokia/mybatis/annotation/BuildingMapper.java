package com.lokia.mybatis.annotation;

import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.annotations.Select;

/**
 * @author gushu
 * @data 2018/8/17
 */
public interface BuildingMapper {

    @Select("SELECT * FROM building where id = #{id}")
    Building getById(String id);

}
