package com.imengyu.datacenter.service;


import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    int AUTH_TOKEN_DEFAULT_EXPIRE_TIME = 3600; //60 Min
    int AUTH_TOKEN_REMBER_EXPIRE_TIME = 1296000; //15 Day

    String AUTH_TOKEN_NAME = "CMAuthToken";
    String AUTH_KEY = "VJjEp43MDcLBVYcmBHRtFLEoq3xfDJR6";

    Result authDoLogin(User user, boolean remember, HttpServletRequest request, HttpServletResponse response);
    Result authDoTest(HttpServletRequest request);
    Result authDoLogout(HttpServletRequest request, HttpServletResponse response, String redirect_uri);

}
