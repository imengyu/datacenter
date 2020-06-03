package com.imengyu.datacenter.service;

import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.utils.Result;

public interface UserService {
    Result<User> getUserById(Integer id);
}
