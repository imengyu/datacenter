package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT id,name,password,name,state FROM t_user WHERE name = #{name}")
    List<User> findByUserName(@Param("name") String name);

    @Update("UPDATE t_users SET state = #{state} FROM t_user WHERE name = #{name}")
    boolean updateUserStateByUserCode(@Param("state") boolean state, @Param("name") String name);

    @Select("SELECT id,name,state,userhead FROM t_user WHERE id = #{id}")
    User getUserById(@Param("id") int id);
}
