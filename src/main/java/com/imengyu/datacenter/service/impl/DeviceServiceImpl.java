package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.mapper.DeviceMapper;
import com.imengyu.datacenter.repository.DeviceRepository;
import com.imengyu.datacenter.service.CommonPermissionCheckService;
import com.imengyu.datacenter.service.DeviceService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.encryption.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private DeviceRepository deviceRepository = null;
  @Autowired
  private DeviceMapper deviceMapper = null;

  @Autowired
  private CommonPermissionCheckService commonPermissionCheckService = null;

  @Override
  public Result<Device> getDevicePageByProductId(Integer id, PageRequest pageRequest, Device searchParam, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(id, request);
    if(!resultPermission.getSuccess()) return (Result)resultPermission.getFailResult();

    if(searchParam == null) {
      return Result.success(deviceRepository.findByProductId(id, pageRequest));
    }else{

      searchParam.setProductId(id);
      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("product_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("mark_name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("enable_state", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("last_up_time", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("create_at", ExampleMatcher.GenericPropertyMatchers.contains());

      Example<Device> sample = Example.of(searchParam, matcher);
      return Result.success(deviceRepository.findAll(sample, pageRequest));
    }

  }

  @Override
  public Result<Device> addDeviceByProductId(Integer id, Device device, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(id, request);
    if(!resultPermission.getSuccess()) return (Result)resultPermission.getFailResult();

    device.setProductId(id);
    device.setCreateAt(new Date());
    device.setAuthSecretKey(MD5Utils.encrypt(("MINI_IOT_DEVICE_" + device.getName()).getBytes()));

    device = deviceRepository.save(device);
    return Result.success(device);
  }

  @Override
  public Result<Device> updateDevice(Integer deviceId, Device device, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(device.getId(), request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    deviceMapper.updateDeviceInfoById(deviceId, device.getName(), device.getMarkName(),
            device.getEnableState(), device.getAuthType());

    return Result.success(device);
  }

  @Override
  public Result<Device> getDeviceById(Integer deviceId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    return Result.success(deviceRepository.findById(deviceId).get());
  }

  @Override
  public Result<Device> deleteDeviceById(Integer deviceId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    deviceRepository.deleteById(deviceId);
    return Result.success();
  }

  @Override
  public Result updateDeviceGroup(Integer deviceId, Integer groupId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    deviceMapper.updateDeviceGroupById(deviceId, groupId);
    return Result.success();
  }


}
