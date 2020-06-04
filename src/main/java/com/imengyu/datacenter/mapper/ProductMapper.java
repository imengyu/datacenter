package com.imengyu.datacenter.mapper;

import com.imengyu.datacenter.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {

  @Update("UPDATE t_product SET name = #{name},type = #{type} WHERE id = #{id}")
  boolean updateProductInfoById(@Param("id") int id, @Param("name") String name, @Param("type") String type);

  @Select("SELECT secret_key FROM t_product WHERE id = #{id}")
  String getProductSecretKeyById(@Param("id") int id);

  @Select("SELECT user_id FROM t_product WHERE id = #{id}")
  Integer getUserIdById(@Param("id") int id);

  @Select("SELECT id, name FROM t_product WHERE user_id = #{userId}")
  List<Product> getProductListByUserId(@Param("userId") int userId);
}
