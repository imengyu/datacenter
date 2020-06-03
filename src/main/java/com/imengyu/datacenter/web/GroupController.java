package com.imengyu.datacenter.web;

import com.imengyu.datacenter.annotation.RequestAuth;
import com.imengyu.datacenter.entity.DeviceGroup;
import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.service.DeviceGroupService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.request.PageRequestUtils;
import com.imengyu.datacenter.utils.tools.UrlQueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private DeviceGroupService deviceGroupService = null;

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
    @GetMapping("/device-group/{pageIndex}/{pageSize}")
    public Result getUserDeviceGroups(
                              @PathVariable("pageIndex") Integer pageIndex,
                              @PathVariable("pageSize") Integer pageSize,
                              @RequestParam(value = "search", required = false, defaultValue = "null") String searchParam) {
        return deviceGroupService.getDeviceGroupPageByUserId(PublicAuth.authGetUseId(request),
                PageRequestUtils.createPageRequestAndSort(pageIndex, pageSize, request),
                UrlQueryTools.getObjectFromUrlJson(searchParam, DeviceGroup.class), request);
    }
    /**
     * 删除产品
     * @param productId 产品ID
     */
    @ResponseBody
    @RequestAuth
    @DeleteMapping("/device-group/{productId}")
    public Result deleteDeviceGroup(@PathVariable("productId") Integer productId) {
        return deviceGroupService.deleteDeviceGroupById(productId, request);
    }

    /**
     * 获取单个产品信息
     * @param productId 产品ID
     */
    @ResponseBody
    @RequestAuth
    @GetMapping("/device-group/{productId}")
    public Result getDeviceGroup(@PathVariable("productId") Integer productId) {
        return deviceGroupService.getDeviceGroupById(productId, request);
    }

    /**
     * 更新单个产品信息
     * @param productId 产品ID
     * @param product 产品
     */
    @ResponseBody
    @RequestAuth
    @PutMapping("/device-group/{productId}")
    public Result updateDeviceGroup(@PathVariable("productId") Integer productId, @RequestBody DeviceGroup product) {
        return deviceGroupService.updateDeviceGroup(product, request);
    }

    /**
     * 添加产品信息
     * @param product 产品
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/device-group")
    public Result addDeviceGroup(@RequestBody DeviceGroup product) {
        return deviceGroupService.addDeviceGroupByUserId(PublicAuth.authGetUseId(request), product, request);
    }

}
