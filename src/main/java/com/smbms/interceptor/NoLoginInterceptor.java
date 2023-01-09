package com.smbms.interceptor;

import com.smbms.dao.UserMapper;
import com.smbms.exceptions.NoLoginException;
import com.smbms.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        System.out.println("前端传的userId："+userId);

        if (userId == null || userMapper.selectById(userId) == null ){

            throw new NoLoginException();
        }

        return true;

//        return super.preHandle(request, response, handler);
    }
}
