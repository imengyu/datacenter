package com.imengyu.datacenter.service.impl;

import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.mapper.UserMapper;
import com.imengyu.datacenter.service.UserService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        if(user != null) return Result.success(user);
        else return Result.failure(ResultCodeEnum.NOT_FOUNT);
    }
}
