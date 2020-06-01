package com.imengyu.datacenter.service.impl;


import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.exception.InvalidArgumentException;
import com.imengyu.datacenter.mapper.UserMapper;
import com.imengyu.datacenter.service.AuthService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import com.imengyu.datacenter.utils.StringUtils;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.auth.TokenAuthUtils;
import com.imengyu.datacenter.utils.encryption.AESUtils;
import com.imengyu.datacenter.utils.request.CookieUtils;
import com.imengyu.datacenter.utils.request.IpUtil;
import com.imengyu.datacenter.utils.response.AuthCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public Result authDoLogin(User user, boolean remember, HttpServletRequest request, HttpServletResponse response) {

        String userName = user.getName();
        String userPass = user.getPassword();


        if(StringUtils.isBlank(userName) || StringUtils.isBlank(userPass))
            return Result.failure(ResultCodeEnum.PARAM_ERROR);

        List<User> users = userMapper.findByUserName(userName);
        if(users.size() == 0)
            return Result.failure(ResultCodeEnum.NOT_FOUNT);

        User userReal = users.get(0);

        if(!userReal.getState() && userReal.getId() != 1)
            return Result.failure(ResultCodeEnum.USER_BANNED);
        if(!userReal.getPassword().equals(AESUtils.encrypt(userPass + "$" + userName, AUTH_KEY)))
            return Result.failure(ResultCodeEnum.PASS_ERROR);

        String token = "";

        try {
            String ip = IpUtil.getIpAddr(request);
            token = TokenAuthUtils.genToken(remember ? AUTH_TOKEN_REMBER_EXPIRE_TIME : AUTH_TOKEN_DEFAULT_EXPIRE_TIME,ip + "#" + userReal.getId() + "#0#0");
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        CookieUtils.setCookie(response, AUTH_TOKEN_NAME, token);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userReal.getId());
        result.put("authToken", token);
        result.put("authTokenName", AUTH_TOKEN_NAME);
        result.put("expireSecond", remember ? AUTH_TOKEN_REMBER_EXPIRE_TIME : AUTH_TOKEN_DEFAULT_EXPIRE_TIME);
        return Result.success(result);
    }

    @Override
    public Result authDoTest(HttpServletRequest request) {
        if(testAuth(request)) {
            Map<String, Object> result = new HashMap<>();
            result.put("userId", PublicAuth.authGetUseId(request));
            return Result.success(result);
        }
        return Result.failure(ResultCodeEnum.FAILED_AUTH);
    }

    @Override
    public Result authDoLogout(HttpServletRequest request, HttpServletResponse response, String redirect_uri) {

        if(!testAuth(request))
            return Result.failure(ResultCodeEnum.FAILED_AUTH);
        CookieUtils.setCookie(response, AuthService.AUTH_TOKEN_NAME, "", 0);
        if(!StringUtils.isBlank(redirect_uri)) {
            try { response.sendRedirect(redirect_uri); } catch (IOException e) { e.printStackTrace(); }
            return Result.success();
        }else {
            Map<String, Object> result = new HashMap<>();
            result.put("authTokenName", AUTH_TOKEN_NAME);
            return Result.success(result);
        }

    }

    private boolean testAuth(HttpServletRequest request) {
        return PublicAuth.authCheck(request) >= AuthCode.SUCCESS;
    }
}
