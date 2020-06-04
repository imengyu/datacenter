package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.DeviceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceGroupMapper {

  @Update("UPDATE t_device_group SET remarks = #{remarks} WHERE id = #{id}")
  boolean updateDeviceInfoById(@Param("id") int id, @Param("remarks") String remarks);

  @Select("SELECT user_id FROM t_device_group WHERE id = #{id}")
  Integer getUserIdById(@Param("id") int id);

  @Select("SELECT name,id,parent_id FROM t_device_group WHERE id = #{id}")
  DeviceGroup getIdAndNameById(@Param("id") int id);

  @Select("SELECT name,id,parent_id FROM t_device_group WHERE parent_id = #{parentId}")
  List<DeviceGroup> getAllIdAndNameByParentId(@Param("parentId") int parentId);

  @Select("SELECT name,id,parent_id FROM t_device_group WHERE user_id = #{userId}")
  List<DeviceGroup> getAllIdAndNameByUserId(@Param("userId") int userId);

  @Select("SELECT name,id,parent_id FROM t_device_group WHERE user_id = #{userId} AND parent_id = 0")
  List<DeviceGroup> getAllIdAndNameByUserIdAndNoLeaf(@Param("userId") int userId);
}
