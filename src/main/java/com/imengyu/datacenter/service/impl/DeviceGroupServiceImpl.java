package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.DeviceGroup;
import com.imengyu.datacenter.repository.DeviceGroupRepository;
import com.imengyu.datacenter.service.DeviceGroupService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import com.imengyu.datacenter.utils.auth.PublicAuth;
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

  @Override
  public Result getDeviceGroupPageByUserId(Integer id, PageRequest pageRequest, DeviceGroup searchParam, HttpServletRequest request) {
    int userId = PublicAuth.authGetUseId(request);
    if(userId != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    if(searchParam == null) {
      return Result.success(deviceGroupRepository.findByUserId(id, pageRequest));
    }else{

      searchParam.setUserId(id);
      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("user_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("create_at", ExampleMatcher.GenericPropertyMatchers.contains());

      Example<DeviceGroup> sample = Example.of(searchParam, matcher);
      return Result.success(deviceGroupRepository.findAll(sample, pageRequest));
    }

  }

  @Override
  public Result addDeviceGroupByUserId(Integer id, DeviceGroup product, HttpServletRequest request) {

    int userId = PublicAuth.authGetUseId(request);
    if(userId != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    product.setUserId(userId);
    product.setCreateAt(new Date());

    product = deviceGroupRepository.save(product);
    return Result.success(product);
  }

  @Override
  public Result updateDeviceGroup(DeviceGroup product, HttpServletRequest request) {

    Optional<DeviceGroup> productOld = deviceGroupRepository.findById(product.getId());
    int userId = PublicAuth.authGetUseId(request);

    if(!productOld.isPresent()) return Result.failure(ResultCodeEnum.NOT_FOUNT);
    if(userId != productOld.get().getUserId()) return Result.failure(ResultCodeEnum.FORIBBEN);

    product.setUserId(userId);
    deviceGroupRepository.save(product);
    return Result.success(product);
  }

  @Override
  public Result getDeviceGroupById(Integer productId, HttpServletRequest request) {
    Optional<DeviceGroup> productOld = deviceGroupRepository.findById(productId);
    int userId = PublicAuth.authGetUseId(request);

    if(!productOld.isPresent()) return Result.failure(ResultCodeEnum.NOT_FOUNT);
    if(userId != productOld.get().getUserId()) return Result.failure(ResultCodeEnum.FORIBBEN);

    return Result.success(productOld.get());
  }

  @Override
  public Result deleteDeviceGroupById(Integer productId, HttpServletRequest request) {

    Optional<DeviceGroup> productOld = deviceGroupRepository.findById(productId);
    int userId = PublicAuth.authGetUseId(request);

    if(!productOld.isPresent()) return Result.failure(ResultCodeEnum.NOT_FOUNT);
    if(userId != productOld.get().getUserId()) return Result.failure(ResultCodeEnum.FORIBBEN);

    deviceGroupRepository.deleteById(productId);
    return Result.success();
  }

}
