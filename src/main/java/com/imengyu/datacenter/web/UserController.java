package com.imengyu.datacenter.web;


import com.imengyu.datacenter.annotation.RequestAuth;
import com.imengyu.datacenter.service.UserService;
import com.imengyu.datacenter.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestAuth
    @ResponseBody
    @GetMapping("/user/{userId}")
    public Result getUserInfo(@PathVariable("userId") Integer userId) {
        return userService.getUserById(userId);
    }
}
