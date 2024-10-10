package com.xh.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.configuration.CustomObjectMapper;
import com.xh.constant.Constants;
import com.xh.core.RedisTemplate;
import com.xh.entity.XhLoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-01 22:12
 **/
//登录拦截器
    //写完之后记得注册上去
public class LoginInterceptor implements HandlerInterceptor {
    //redis模板
    @Resource
    private RedisTemplate redisTemplate;
    //序列化工具
    @Resource
    private CustomObjectMapper objectMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        // 创建一个 ResponseEntity 对象，表示 HTTP 响应
//        // 设置 HTTP 状态码为 401（未授权），并设置响应体为 "Bad Credentials!" 错误信息
//        ResponseEntity<String> res = ResponseEntity.status(401).body("Bad Credentials!");
//        //如果不是白名单的请求，检测
//        // 判断有没有Authorization这个请求头，拿到首部的请求头
//        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
//
//
//        if(token == null){
//            response.setStatus(401);
//            response.getWriter().write(objectMapper.writeValueAsString(res));
//            return false;
//        }
//        //        String tokenKey = Constants.TOKEN_PREFIX + request.getHeader("username") + ":" + token;
//        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
//        if(keys == null || keys.size() == 0){
//            response.setStatus(401);
//            response.getWriter().write(objectMapper.writeValueAsString(res));
//            return false;
//
//        }
//        String tokenKey = (String) keys.toArray()[0];
//        //3.使用token去redis中查看，有没有对应的loginUser
//        XhLoginUser xhLoginUser = redisTemplate.getObject(tokenKey, new TypeReference<>() {});
//
//        //如果用户不存在（token不存在）
//        if(xhLoginUser == null){
//            // 使用 Jackson 的 ObjectMapper 将 ResponseEntity 对象转换为 JSON 字符串
//            // 然后通过 HttpServletResponse 的 PrintWriter 将该 JSON 写入到 HTTP 响应中
//            response.setStatus(401);
//            response.getWriter().write(objectMapper.writeValueAsString(res));
//            return false;
//        }
//        //给token续命
//        redisTemplate.expire(tokenKey,Constants.TOKEN_TIME);
        return true;

    }
}
