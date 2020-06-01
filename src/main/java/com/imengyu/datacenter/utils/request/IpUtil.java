package com.imengyu.datacenter.utils.request;

import com.dreamfish.customersystem.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    /**
     * 获取用户IP地址
     * @param request 请求
     * @return 返回用户IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
            //修复获取 0:0:0:0:0:0:0:1 的错误, 说明服务端和客户端在一台机器
            if (!StringUtils.isEmpty(ipAddress) && "0:0:0:0:0:0:0:1".equals(ipAddress)) { // "***.***.***.***".length()
                ipAddress = "127.0.0.1";
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
