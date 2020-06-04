package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.mapper.DeviceMapper;
import com.imengyu.datacenter.repository.DeviceRepository;
import com.imengyu.datacenter.service.CommonPermissionCheckService;
import com.imengyu.datacenter.service.DeviceService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.encryption.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private DeviceRepository deviceRepository = null;
  @Autowired
  private DeviceMapper deviceMapper = null;

  @Autowired
  private CommonPermissionCheckService commonPermissionCheckService = null;

  @Override
  public Result<Device> getDevicePageByUserId(Integer id, PageRequest pageRequest, Device searchParam, HttpServletRequest request) {

    if(PublicAuth.authGetUseId(request).intValue() != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    if(searchParam == null) {
      return Result.success(deviceRepository.findByUserId(id, pageRequest));
    }else{

      searchParam.setUserId(id);
      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("user_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("product_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("group_id", ExampleMatcher.GenericPropertyMatchers.exact())
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
  public Result<Device> addDeviceByProductId(Device device, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(device.getProductId(), request);
    if(!resultPermission.getSuccess()) return (Result)resultPermission.getFailResult();
    
    device.setUserId(resultPermission.getAuthSuccessUserId());
    device.setCreateAt(new Date());
    device.setAuthSecretKey(MD5Utils.encrypt(("MINI_IOT_DEVICE_" + device.getName()).getBytes()));

    device = deviceRepository.save(device);
    return Result.success(device);
  }

  @Override
  public Result<Device> updateDevice(Integer deviceId, Device device, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(device.getId(), request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    deviceMapper.updateDeviceInfoById(deviceId, device.getName(), device.getRemarks(),
            device.getEnableState(), device.getAuthType());

    return Result.success(device);
  }

  @Override
  public Result<Device> updateDeviceEnableState(Integer deviceId, Boolean enable, HttpServletRequest request) {
    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    deviceMapper.updateDeviceEnableStateById(deviceId, enable);
    return Result.success();
  }

  @Override
  public Result<Device> getDeviceState(Integer deviceId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    return Result.success(deviceMapper.getDeviceStatusById(deviceId));
  }

  @Override
  public Result<Device> getDeviceById(Integer deviceId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkDevicePermission(deviceId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    return Result.success(deviceRepository.findById(deviceId).get());
  }

  @Override
  public Result getAllDeviceOverview(HttpServletRequest request) {

    Integer userId = PublicAuth.authGetUseId(request);
    Map<String, Integer> overviewData = new HashMap<>();
    overviewData.put("activatedCount", deviceMapper.getDeviceActivatedCountByUserId(userId));
    overviewData.put("connectedCount", deviceMapper.getDeviceConnectedCountByUserId(userId));
    overviewData.put("allCount", deviceMapper.getDeviceCountByUserId(userId));
    return Result.success(overviewData);
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
