package com.imengyu.datacenter.interceptor;

import com.imengyu.datacenter.annotation.RequestAuth;
import com.imengyu.datacenter.utils.Result;
import com.imengyu.datacenter.utils.StringUtils;
import com.imengyu.datacenter.utils.auth.PublicAuth;
import com.imengyu.datacenter.utils.response.AuthCode;
import com.imengyu.datacenter.utils.response.ResponseUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自动验证注解拦截器
 */
public class RequestAuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //First auth user and user level
        RequestAuth requestAuth = method.getAnnotation(RequestAuth.class);
        if (requestAuth != null && requestAuth.required()) {
            int authCode = PublicAuth.authCheck(request);
            if(authCode < AuthCode.SUCCESS){
                if(StringUtils.isBlank(requestAuth.redirectTo())) {
                    Result result = Result.failure(requestAuth.unauthCode(), requestAuth.unauthMsg(), String.valueOf(authCode));
                    response.setStatus(Integer.parseInt(requestAuth.unauthCode()));
                    ResponseUtils.responseOutWithJson(response, result);
                }else{
                    response.sendRedirect(requestAuth.redirectTo() + "?error=" +
                            (authCode == AuthCode.FAIL_EXPIRED ? "SessionOut" : "RequestLogin") + "&redirect_url=" + request.getRequestURI());
                }
                return false;
            }
        }


        return true;
    }

}
