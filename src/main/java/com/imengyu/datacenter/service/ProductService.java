package com.imengyu.datacenter.service;

import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.utils.Result;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
  Result<Product> getProductListByUserId(Integer userId, HttpServletRequest request);
  Result<Product> getProductPageByUserId(Integer id, PageRequest pageRequest, Product searchParam, HttpServletRequest request);
  Result<Product> addProductByUserId(Integer id, Product product, HttpServletRequest request);
  Result<Product> updateProduct(Product product, HttpServletRequest request);
  Result<Product> getProductById(Integer productId, HttpServletRequest request);
  Result deleteProductById(Integer productId, HttpServletRequest request);
  Result getProductSecretKeyById(Integer productId, String userPass, HttpServletRequest request);
}
