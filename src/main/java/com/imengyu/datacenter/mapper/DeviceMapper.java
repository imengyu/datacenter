package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DeviceMapper {

  @Update("UPDATE t_device SET name = #{name},enable_state = #{enable_state}," +
          "mark_name = #{mark_name},auth_type = #{auth_type}," +
          " WHERE id = #{id}")
  boolean updateDeviceInfoById(@Param("id") int id,
                               @Param("name") String name,
                               @Param("mark_name") String mark_name,
                               @Param("enable_state") int enable_state,
                               @Param("auth_type") int auth_type);

  @Update("UPDATE t_device SET group_id = #{group_id} WHERE id = #{id}")
  boolean updateDeviceGroupById(@Param("id") int id,
                               @Param("group_id") int group_id);

  @Select("SELECT auth_secret_key FROM t_device WHERE id = #{id}")
  String getDeviceSecretKeyById(@Param("id") int id);

  @Select("SELECT product_id FROM t_device WHERE id = #{id}")
  Integer findProductIdById(@Param("id") int id);


}
