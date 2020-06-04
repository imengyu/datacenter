package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceMapper {

  @Update("UPDATE t_device SET name = #{name},enable_state = #{enable_state}," +
          "mark_name = #{mark_name},auth_type = #{auth_type}," +
          " WHERE id = #{id}")
  void updateDeviceInfoById(@Param("id") int id,
                               @Param("name") String name,
                               @Param("mark_name") String mark_name,
                               @Param("enable_state") int enable_state,
                               @Param("auth_type") int auth_type);

  @Update("UPDATE t_device SET group_id = #{group_id} WHERE id = #{id}")
  void updateDeviceGroupById(@Param("id") int id,
                               @Param("group_id") int group_id);

  @Update("UPDATE t_device SET enable_state = #{enable_state} WHERE id = #{id}")
  void updateDeviceEnableStateById(@Param("id") int id,
                                @Param("enable_state") boolean enable_state);

  @Select("SELECT auth_secret_key FROM t_device WHERE id = #{id}")
  String getDeviceSecretKeyById(@Param("id") int id);

  @Select("SELECT id,now_state,last_up_time,current_connect_ip FROM t_device WHERE id = #{id}")
  List<Device> getDeviceStatusById(@Param("id") int id);

  @Select("SELECT product_id FROM t_device WHERE id = #{id}")
  Integer getProductIdById(@Param("id") int id);

  @Select("SELECT COUNT(*) FROM t_device WHERE user_id = #{id}")
  Integer getDeviceCountByUserId(@Param("id") int id);

  @Select("SELECT COUNT(*) FROM t_device WHERE user_id = #{id} AND activated = 1")
  Integer getDeviceActivatedCountByUserId(@Param("id") int id);

  @Select("SELECT COUNT(*) FROM t_device WHERE user_id = #{id} AND now_state = 2")
  Integer getDeviceConnectedCountByUserId(@Param("id") int id);
}
