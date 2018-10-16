package com.lokia.mybatis.annotation.mapper;

import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author gushu
 * @data 2018/8/17
 */
public interface BuildingMapper {

    @Select("SELECT * FROM building where id = #{id}")
    Building getById(String id);

    @Select("SELECT * FROM building where id = #{id}")
    Building getById4FirstCache(String id);

    @Update("update building set name = #{name} where id=#{id}")
    int updateName(Building building);

    Building getById4SecondCache(@Param("id") String id);
}
