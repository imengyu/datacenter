package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.CheckPermissionResult;
import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.mapper.ProductMapper;
import com.imengyu.datacenter.mapper.UserMapper;
import com.imengyu.datacenter.repository.ProductRepository;
import com.imengyu.datacenter.service.CommonPermissionCheckService;
import com.imengyu.datacenter.service.ProductService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.encryption.AESUtils;
import com.imengyu.datacenter.utils.encryption.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

import static com.imengyu.datacenter.service.AuthService.AUTH_KEY;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository = null;
  @Autowired
  private ProductMapper productMapper = null;
  @Autowired
  private UserMapper userMapper = null;

  @Autowired
  private CommonPermissionCheckService commonPermissionCheckService = null;


  @Override
  public Result<Product> getProductListByUserId(Integer userId, HttpServletRequest request) {

    if(PublicAuth.authGetUseId(request).intValue() != userId) return Result.failure(ResultCodeEnum.FORIBBEN);

    return Result.success(productMapper.getProductListByUserId(userId));
  }

  @Override
  public Result<Product> getProductPageByUserId(Integer id, PageRequest pageRequest,
                                            Product searchParam, HttpServletRequest request) {
    if(PublicAuth.authGetUseId(request).intValue() != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    if(searchParam == null) {
      return Result.success(productRepository.findByUserId(id, pageRequest));
    }else{
      searchParam.setUserId(id);

      ExampleMatcher matcher = ExampleMatcher.matching()
              .withMatcher("user_id", ExampleMatcher.GenericPropertyMatchers.exact())
              .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
              .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
      Example<Product> sample = Example.of(searchParam, matcher);
      return Result.success(productRepository.findAll(sample, pageRequest));
    }


  }

  @Override
  public Result<Product> addProductByUserId(Integer id, Product product, HttpServletRequest request) {

    int userId = PublicAuth.authGetUseId(request);
    if(userId != id) return Result.failure(ResultCodeEnum.FORIBBEN);

    product.setUserId(userId);
    product.setCreateAt(new Date());
    product.setProductKey(MD5Utils.encrypt(("MINI_IOT_" + product.getName()).getBytes()));
    product.setSecretKey(MD5Utils.encrypt((new Date().getTime() + "_MINI_IOT_K_" + product.getName()).getBytes()));

    product = productRepository.save(product);
    return Result.success(product);
  }

  @Override
  public Result<Product> updateProduct(Product product, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(product.getId(), request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    productMapper.updateProductInfoById(product.getId(), product.getName(), product.getType());
    return Result.success(product);
  }

  @Override
  public Result<Product> getProductById(Integer productId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(productId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    return Result.success(productRepository.findById(productId).get());
  }

  @Override
  public Result deleteProductById(Integer productId, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(productId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    productRepository.deleteById(productId);
    return Result.success();
  }

  @Override
  public Result getProductSecretKeyById(Integer productId, String userPass, HttpServletRequest request) {

    CheckPermissionResult resultPermission = commonPermissionCheckService.checkProductPermission(productId, request);
    if(!resultPermission.getSuccess()) return resultPermission.getFailResult();

    int userId = PublicAuth.authGetUseId(request);
    //验证用户密码
    User user = userMapper.getUserById(userId);
    if(!user.getPassword().equals(AESUtils.encrypt(userPass + "$" + user.getName(), AUTH_KEY)))
      return Result.failure(ResultCodeEnum.PASS_ERROR);

    return Result.success(productMapper.getProductSecretKeyById(productId));
  }
}
