package com.imengyu.datacenter.web;

import com.imengyu.datacenter.annotation.RequestAuth;
import com.imengyu.datacenter.entity.Device;
import com.imengyu.datacenter.entity.Product;
import com.imengyu.datacenter.service.DeviceService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.request.PageRequestUtils;
import com.imengyu.datacenter.utils.tools.UrlQueryTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceService deviceService = null;

    @Autowired
    private HttpServletRequest request = null;
    @Autowired
    private HttpServletResponse response = null;

    /**
     * 获取当前用户所有设备分页
     * @param pageIndex 页码
     * @param pageSize 页大小
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/product/{productId}/device/{pageIndex}/{pageSize}")
    public Result getProductDevices(
                            @PathVariable("productId") Integer productId,
                            @PathVariable("pageIndex") Integer pageIndex,
                            @PathVariable("pageSize") Integer pageSize,
                            @RequestParam(value = "search", required = false, defaultValue = "null") String searchParam) {
        return deviceService.getDevicePageByProductId(productId,
                PageRequestUtils.createPageRequestAndSort(pageIndex, pageSize, request),
                UrlQueryTools.getObjectFromUrlJson(searchParam, Device.class), request);
    }


    /**
     * 删除设备
     * @param deviceId 设备ID
     */
    @ResponseBody
    @RequestAuth
    @DeleteMapping("/product/{productId}/device/{deviceId}")
    public Result deleteDevice(@PathVariable("deviceId") Integer deviceId) {
        return deviceService.deleteDeviceById(deviceId, request);
    }

    /**
     * 获取单个设备信息
     * @param deviceId 设备ID
     */
    @ResponseBody
    @RequestAuth
    @GetMapping("/product/{productId}/device/{deviceId}")
    public Result getDevice(@PathVariable("deviceId") Integer deviceId) {
        return deviceService.getDeviceById(deviceId, request);
    }

    /**
     * 更新单个设备信息
     * @param deviceId 设备ID
     * @param device 设备
     */
    @ResponseBody
    @RequestAuth
    @PutMapping("/product/{productId}/device/{deviceId}")
    public Result updateDevice(@PathVariable("deviceId") Integer deviceId, @RequestBody Device device) {
        return deviceService.updateDevice(deviceId, device, request);
    }

    /**
     * 更新设备分组信息
     * @param deviceId 设备ID
     * @param groupId 组ID
     */
    @ResponseBody
    @RequestAuth
    @PutMapping("/product/{productId}/device/{deviceId}/group/{groupId}")
    public Result updateDeviceGroup(@PathVariable("deviceId") Integer deviceId, @PathVariable("groupId") Integer groupId) {
        return deviceService.updateDeviceGroup(deviceId, groupId, request);
    }

    /**
     * 添加设备信息
     * @param device 设备
     * @param productId 设备ID
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/product/{productId}/device")
    public Result addDevice(@PathVariable("productId") Integer productId, @RequestBody Device device) {
        return deviceService.addDeviceByProductId(productId, device, request);
    }

}
