package com.cloud.common.web;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : lukew
 * @project : IDEA
 * @created : 2018/5/5 22:26
 * @email : 13507615840@163.com
 * @gitHub : https://github.com/lukw510903926
 * @description :
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return false;
    }
}
