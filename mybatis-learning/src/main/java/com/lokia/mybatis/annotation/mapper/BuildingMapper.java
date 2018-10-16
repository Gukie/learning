package com.lokia.mybatis.annotation.mapper;

import com.lokia.mybatis.annotation.provider.BuildingSelectProvider;
import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @SelectProvider(type = BuildingSelectProvider.class,method = "getByName")
    List<Building> getByName(@Param("name") String name,@Param("orderBy") String orderBy);
}
