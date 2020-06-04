package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.DeviceGroup;
import com.imengyu.datacenter.mapper.DeviceGroupMapper;
import com.imengyu.datacenter.repository.DeviceGroupRepository;
import com.imengyu.datacenter.service.CommonPermissionCheckService;
import com.imengyu.datacenter.service.DeviceGroupService;
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
import java.util.Optional;

@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

  @Autowired
  private DeviceGroupRepository deviceGroupRepository = null;
  @Autowired
  private DeviceGroupMapper deviceGroupMapper = null;

  @Autowired
  private CommonPermissionCheckService commonPermissionCheckService = null;

  @Override
  public Result getDeviceGroupPageByUserId(Integer id, PageRequest pageRequest, DeviceGroup searchParam, HttpServletRequest request) {
    int userId = PublicAuth.authGetUseId(request);
    if(userId != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    if(searchParam == null) {
      return Result.success(deviceGroupRepository.findByUserIdAndParentId(id, 0, pageRequest));
    }else{

      searchParam.setUserId(id);
      searchParam.setParentId(0);

      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("user_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("parent_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("create_at", ExampleMatcher.GenericPropertyMatchers.contains());

      Example<DeviceGroup> sample = Example.of(searchParam, matcher);
      return Result.success(deviceGroupRepository.findAll(sample, pageRequest));
    }

  }

  @Override
  public Result getDeviceGroupPageByParentId(Integer id, PageRequest pageRequest, DeviceGroup searchParam, HttpServletRequest request) {

    CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(id, request);
    if(!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

    if(searchParam == null) {
      return Result.success(deviceGroupRepository.findByParentId(id, pageRequest));
    }else{

      searchParam.setParentId(id);
      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("parent_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("create_at", ExampleMatcher.GenericPropertyMatchers.contains());

      Example<DeviceGroup> sample = Example.of(searchParam, matcher);
      return Result.success(deviceGroupRepository.findAll(sample, pageRequest));
    }

  }

  @Override
  public Result addDeviceGroupByUserId(Integer id, DeviceGroup group, HttpServletRequest request) {

    int userId = PublicAuth.authGetUseId(request);
    if(userId != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    group.setUserId(userId);
    group.setCreateAt(new Date());
    group.setIdentifier(MD5Utils.encrypt((userId + "_" + group.getName() + new Date().getTime()).getBytes()));

    group = deviceGroupRepository.save(group);
    return Result.success(group);
  }

  @Override
  public Result addDeviceGroupByParentId(Integer id, DeviceGroup group, HttpServletRequest request) {

    CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(id, request);
    if(!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

    int userId = PublicAuth.authGetUseId(request);

    group.setParentId(id);
    group.setUserId(userId);
    group.setCreateAt(new Date());
    group.setIdentifier(MD5Utils.encrypt((userId + "_" + group.getName() + new Date().getTime()).getBytes()));

    group = deviceGroupRepository.save(group);
    return Result.success(group);
  }

  @Override
  public Result updateDeviceGroup(DeviceGroup group, HttpServletRequest request) {

    CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(group.getId(), request);
    if(!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

    deviceGroupMapper.updateDeviceInfoById(group.getId(), group.getRemarks());
    return Result.success(group);
  }

  @Override
  public Result getDeviceGroupById(Integer groupId, HttpServletRequest request) {

    CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(groupId, request);
    if(!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

    return Result.success(deviceGroupRepository.findById(groupId).get());
  }

  @Override
  public Result<DeviceGroup> getDeviceGroupTreeByUserId(Integer userId, HttpServletRequest request) {

    if(PublicAuth.authGetUseId(request).intValue() != userId) return Result.failure(ResultCodeEnum.FORIBBEN);

    return Result.success(deviceGroupMapper.getAllIdAndNameByUserId(userId));
  }

  @Override
  public Result<DeviceGroup> getDeviceGroupTreeByParentId(Integer parentId, HttpServletRequest request) {

    if(parentId > 0) {
      CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(parentId, request);
      if (!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

      return Result.success(deviceGroupMapper.getAllIdAndNameByParentId(parentId));
    }else {
      return Result.success(deviceGroupMapper.getAllIdAndNameByUserIdAndNoLeaf(PublicAuth.authGetUseId(request)));
    }
  }

  @Override
  public Result deleteDeviceGroupById(Integer groupId, HttpServletRequest request) {

    CheckPermissionResult checkPermissionResult = commonPermissionCheckService.checkDeviceGroupPermission(groupId, request);
    if(!checkPermissionResult.getSuccess()) return checkPermissionResult.getFailResult();

    deviceGroupRepository.deleteById(groupId);
    return Result.success();
  }

}
