package com.imengyu.datacenter.service;

import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.DeviceGroup;
import com.imengyu.datacenter.utils.Result;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

public interface DeviceGroupService {
  Result<DeviceGroup> getDeviceGroupPageByParentId(Integer id, PageRequest pageRequest, DeviceGroup searchParam, HttpServletRequest request);
  Result<DeviceGroup> getDeviceGroupPageByUserId(Integer id, PageRequest pageRequest, DeviceGroup searchParam, HttpServletRequest request);
  Result<DeviceGroup> addDeviceGroupByUserId(Integer id, DeviceGroup product, HttpServletRequest request);
  Result<DeviceGroup> addDeviceGroupByParentId(Integer id, DeviceGroup product, HttpServletRequest request);
  Result<DeviceGroup> updateDeviceGroup(DeviceGroup product, HttpServletRequest request);
  Result<DeviceGroup> getDeviceGroupById(Integer productId, HttpServletRequest request);

  Result<DeviceGroup> getDeviceGroupTreeByUserId(Integer userId, HttpServletRequest request);
  Result<DeviceGroup> getDeviceGroupTreeByParentId(Integer parentId, HttpServletRequest request);

  Result deleteDeviceGroupById(Integer productId, HttpServletRequest request);
}
