package com.imengyu.datacenter.web;

import com.imengyu.datacenter.entity.User;
import com.imengyu.datacenter.service.AuthService;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.encryption.AESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService = null;

    @Autowired
    private HttpServletRequest request = null;
    @Autowired
    private HttpServletResponse response = null;

    //开始认证 登录
    @ResponseBody
    @PostMapping(value = "", name = "开始认证")
    public Result authEntry(@RequestBody @NonNull User user,
                            @RequestParam(value = "remember", required = false, defaultValue = "false") boolean remember) {
        return authService.authDoLogin(user, remember, request, response);
    }
    //检测认证状态
    @ResponseBody
    @GetMapping(value = "", name = "检测认证状态")
    public Result authTest() {
        return authService.authDoTest(request);
    }

    //结束认证 退出
    @ResponseBody
    @GetMapping(value = "/end", name = "结束认证")
    public Result authEnd(@RequestParam(value = "redirect_uri", required = false) String redirect_uri) throws IOException {
        return authService.authDoLogout(request, response, redirect_uri);
    }

    @ResponseBody
    @GetMapping(value = "/gen-pass", name = "结束认证")
    public String authDebugTestPASSWORD(@RequestParam(value = "pass") String pass, @RequestParam(value = "name") String name) throws IOException {
        return AESUtils.encrypt(pass + "$" + name, AuthService.AUTH_KEY);
    }
}
