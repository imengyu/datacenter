package com.imengyu.datacenter.service;

import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.utils.Result;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

public interface DeviceService {
  Result<Device> getDevicePageByProductId(Integer id, PageRequest pageRequest, Device searchParam, HttpServletRequest request);
  Result<Device> addDeviceByProductId(Integer id, Device product, HttpServletRequest request);
  Result<Device> updateDevice(Integer deviceId, Device product, HttpServletRequest request);
  Result<Device> getDeviceById(Integer deviceId, HttpServletRequest request);
  Result deleteDeviceById(Integer deviceId, HttpServletRequest request);
  Result updateDeviceGroup(Integer deviceId, Integer groupId, HttpServletRequest request);
}
