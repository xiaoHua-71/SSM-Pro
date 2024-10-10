package com.xh.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.constant.Constants;
import com.xh.core.RedisTemplate;
import com.xh.entity.XhLoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-05 15:50
 **/

public class BaseController {
    @Resource
    private RedisTemplate redisTemplate;

    protected XhLoginUser getLoginUser(){
        //获取头部信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 判断有没有Authorization这个请求头，拿到首部的请求头
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        if(token == null){
            throw new RuntimeException("当前用户未登录！");
        }
        //        String tokenKey = Constants.TOKEN_PREFIX + request.getHeader("username") + ":" + token;
        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
        if(keys == null || keys.size() == 0){
            throw new RuntimeException("当前用户未登录！");

        }
        String tokenKey = (String) keys.toArray()[0];
        //3.使用token去redis中查看，有没有对应的loginUser
        return redisTemplate.getObject(tokenKey, new TypeReference<>() {});

    }
}
