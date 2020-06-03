package com.imengyu.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {

  @Update("UPDATE t_product SET name = #{name},type = #{type} WHERE id = #{id}")
  boolean updateProductInfoById(@Param("id") int id, @Param("name") String name, @Param("type") String type);

  @Select("SELECT secret_key FROM t_product WHERE id = #{id}")
  String getProductSecretKeyById(@Param("id") int id);

  @Select("SELECT user_id FROM t_product WHERE id = #{id}")
  Integer getUserIdById(@Param("id") int id);

}
