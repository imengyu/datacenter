package com.imengyu.datacenter.service;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.Product;

import javax.servlet.http.HttpServletRequest;

public interface CommonPermissionCheckService {

  /**
   * 检查当前用户是否对指定设备有权限
   * @param deviceId 设备ID
   * @param request 请求
   */
  CheckPermissionResult checkDevicePermission(Integer deviceId, HttpServletRequest request);
  /**
   * 检查当前用户是否对指定产品有权限
   * @param productId 产品ID
   * @param request 请求
   */
  CheckPermissionResult checkProductPermission(Integer productId, HttpServletRequest request);


}
