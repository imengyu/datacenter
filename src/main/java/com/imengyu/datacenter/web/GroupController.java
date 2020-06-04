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
     * 获取当前用户所有分组分页
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
     * 获取当前用户所有分组分页
     * @param pageIndex 页码
     * @param pageSize 页大小
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/device-group/{parentId}/group/{pageIndex}/{pageSize}")
    public Result getDeviceGroupsByParentId(
            @PathVariable("parentId") Integer parentId,
            @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("pageSize") Integer pageSize,
            @RequestParam(value = "search", required = false, defaultValue = "null") String searchParam) {
        return deviceGroupService.getDeviceGroupPageByParentId(parentId,
                PageRequestUtils.createPageRequestAndSort(pageIndex, pageSize, request),
                UrlQueryTools.getObjectFromUrlJson(searchParam, DeviceGroup.class), request);
    }


    /**
     * 删除分组
     * @param groupId 分组ID
     */
    @ResponseBody
    @RequestAuth
    @DeleteMapping("/device-group/{groupId}")
    public Result deleteDeviceGroup(@PathVariable("groupId") Integer groupId) {
        return deviceGroupService.deleteDeviceGroupById(groupId, request);
    }

    /**
     * 获取单个分组信息
     * @param groupId 分组ID
     */
    @ResponseBody
    @RequestAuth
    @GetMapping("/device-group/{groupId}")
    public Result getDeviceGroup(@PathVariable("groupId") Integer groupId) {
        return deviceGroupService.getDeviceGroupById(groupId, request);
    }

    /**
     * 更新单个分组信息
     * @param groupId 分组ID
     * @param group 分组
     */
    @ResponseBody
    @RequestAuth
    @PutMapping("/device-group/{groupId}")
    public Result updateDeviceGroup(@PathVariable("groupId") Integer groupId, @RequestBody DeviceGroup group) {
        return deviceGroupService.updateDeviceGroup(group, request);
    }

    /**
     * 添加分组信息
     * @param group 分组
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/device-group")
    public Result addDeviceGroup(@RequestBody DeviceGroup group) {
        return deviceGroupService.addDeviceGroupByUserId(PublicAuth.authGetUseId(request), group, request);
    }

    /**
     * 添加分组信息至指定分组
     * @param group 分组
     */
    @ResponseBody
    @RequestAuth
    @PostMapping("/device-group/{groupId}")
    public Result addDeviceGroupByParentId(@PathVariable("groupId") Integer groupId, @RequestBody DeviceGroup group) {
        return deviceGroupService.addDeviceGroupByParentId(groupId, group, request);
    }



    /**
     * 获取当前用户所有分组（Tree）
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/device-group-tree")
    public Result getUserDeviceGroupsTree() {
        return deviceGroupService.getDeviceGroupTreeByUserId(PublicAuth.authGetUseId(request), request);
    }
    /**
     * 获取当前用户所有分组（Tree）
     */
    @RequestAuth
    @ResponseBody
    @GetMapping("/device-group-tree/{parentId}")
    public Result getUserDeviceGroupsTreeByParentId(@PathVariable("parentId") Integer parentId) {
        return deviceGroupService.getDeviceGroupTreeByParentId(parentId, request);
    }

}
