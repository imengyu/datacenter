package com.imengyu.datacenter.web;

import com.imengyu.datacenter.annotation.RequestAuth;
import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.service.ProductService;
import com.imengyu.datacenter.utils.request.PageRequestUtils;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.tools.UrlQueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request = null;
    @Autowired
    private HttpServletResponse response = null;

    /**
     * 获取当前用户所有产品分页
     * @param pageIndex 页码
     * @param pageSize 页大小
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/product/{pageIndex}/{pageSize}")
    public Result getUserProducts(
                              @PathVariable("pageIndex") Integer pageIndex,
                              @PathVariable("pageSize") Integer pageSize,
                              @RequestParam(value = "search", required = false, defaultValue = "null") String searchParam) {
        return productService.getProductPageByUserId(PublicAuth.authGetUseId(request),
                PageRequestUtils.createPageRequestAndSort(pageIndex, pageSize, request),
                UrlQueryTools.getObjectFromUrlJson(searchParam, Product.class), request);

    }

    /**
     * 获取当前用户所有产品下拉列表数据
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/product-list")
    public Result getUserProductsList() {
        return productService.getProductListByUserId(PublicAuth.authGetUseId(request), request);

    }

    /**
     * 删除产品
     * @param productId 产品ID
     */
    @ResponseBody
    @RequestAuth
    @DeleteMapping("/product/{productId}")
    public Result deleteProduct(@PathVariable("productId") Integer productId) {
        return productService.deleteProductById(productId, request);
    }

    /**
     * 获取单个产品信息
     * @param productId 产品ID
     */
    @ResponseBody
    @RequestAuth
    @GetMapping("/product/{productId}")
    public Result getProduct(@PathVariable("productId") Integer productId) {
        return productService.getProductById(productId, request);
    }

    /**
     * 获取产品密钥
     * @param productId 产品ID
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/product/{productId}/secretKey")
    public Result getProductSecretKey(@PathVariable("productId") Integer productId,
                                      @RequestParam("userPass") String userPass) {
        return productService.getProductSecretKeyById(productId, userPass, request);
    }




    /**
     * 更新单个产品信息
     * @param productId 产品ID
     * @param product 产品
     */
    @ResponseBody
    @RequestAuth
    @PutMapping("/product/{productId}")
    public Result updateProduct(@PathVariable("productId") Integer productId, @RequestBody Product product) {
        return productService.updateProduct(product, request);
    }

    /**
     * 添加产品信息
     * @param product 产品
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/product")
    public Result addProduct(@RequestBody Product product) {
        return productService.addProductByUserId(PublicAuth.authGetUseId(request), product, request);
    }

}
