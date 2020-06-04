package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.mapper.DeviceGroupMapper;
import com.imengyu.datacenter.mapper.DeviceMapper;
import com.imengyu.datacenter.mapper.ProductMapper;
import com.imengyu.datacenter.repository.DeviceRepository;
import com.imengyu.datacenter.repository.ProductRepository;
import com.imengyu.datacenter.service.CommonPermissionCheckService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class CommonPermissionCheckServiceImpl implements CommonPermissionCheckService {

  @Autowired
  private DeviceMapper deviceMapper = null;
  @Autowired
  private ProductMapper productMapper = null;
  @Autowired
  private DeviceGroupMapper deviceGroupMapper = null;

  /**
   * 检查当前用户是否对指定设备有权限
   * @param deviceId 设备ID
   * @param request 请求
   */
  public CheckPermissionResult checkDevicePermission(Integer deviceId, HttpServletRequest request) {

    Integer productId = deviceMapper.getProductIdById(deviceId);
    int userId = PublicAuth.authGetUseId(request);

    if(productId == null)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.NOT_FOUNT));

    Integer productUserId = productMapper.getUserIdById(productId);
    if(productUserId == null)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.NOT_FOUNT));
    if(userId != productUserId)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.FORIBBEN));

    return new CheckPermissionResult(userId);
  }
  /**
   * 检查当前用户是否对指定产品有权限
   * @param productId 产品ID
   * @param request 请求
   */
  public CheckPermissionResult checkProductPermission(Integer productId, HttpServletRequest request) {

    int userId = PublicAuth.authGetUseId(request);
    Integer productUserId = productMapper.getUserIdById(productId);
    if(productUserId == null)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.NOT_FOUNT));
    if(userId != productUserId)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.FORIBBEN));

    return new CheckPermissionResult(userId);
  }
  /**
   * 检查当前用户是否对指定分组有权限
   * @param groupId 分组ID
   * @param request 请求
   */
  public CheckPermissionResult checkDeviceGroupPermission(Integer groupId, HttpServletRequest request) {

    int userId = PublicAuth.authGetUseId(request);
    Integer productUserId = deviceGroupMapper.getUserIdById(groupId);
    if(productUserId == null)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.NOT_FOUNT));
    if(userId != productUserId)
      return new CheckPermissionResult(Result.failure(ResultCodeEnum.FORIBBEN));

    return new CheckPermissionResult(userId);
  }

}
