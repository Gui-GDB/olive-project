package com.gdb.main.config.interceptor;

import com.gdb.main.commons.utils.JWTUtil;
import com.gdb.main.pojo.properties.JWTProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 * @author Mr.Gui
 */
@Slf4j
@Component
public class JWTTokenUserInterceptor implements HandlerInterceptor {
    @Resource
    private JWTProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        // 1.校验JWT字符串
        JWTUtil.decodeToken(token, jwtProperties.getSecretKey());
        return true;
    }
}
