package com.example.dao;

import com.example.entity.Params;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {
    // 基于注解的方式
    @Select("select * from user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE name LIKE CONCAT('%', #{params.name}, '%') AND phone LIKE CONCAT('%', #{params.phone}, '%')")
    List<User> findBySearch(@Param("params") Params params);

    @Select("SELECT * FROM user WHERE name = #{name} limit 1")
    User findByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
    User findByNamePassword(@Param("name") String name, @Param("password") String password);
}
