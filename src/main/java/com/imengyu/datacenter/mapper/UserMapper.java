package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT id,name,password,name,state,code FROM t_users WHERE name = #{name}")
    List<User> findByUserCode(@Param("name") String name);
    @Update("UPDATE t_users SET state = #{state} FROM t_users WHERE name = #{name}")
    boolean updateUserStateByUserCode(@Param("state") boolean state, @Param("name") String name);
    @Select("SELECT id,name,state,code,head_img FROM t_users WHERE id = #{id}")
    User getUserById(@Param("id") int id);
}
