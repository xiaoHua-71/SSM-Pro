package com.xh.interceptor;

import com.xh.configuration.CustomObjectMapper;
import com.xh.constant.Constants;
import com.xh.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-10 21:32
 **/

/**
 * 防止表达重复*
 */
public class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CustomObjectMapper customObjectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            // 获取我们的controller方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 判断是否有防止重复提交的注解
            Repeat annotation = method.getAnnotation(Repeat.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    // 如果确定是重复提交，直接响应失败
                    ResponseEntity<String> body = ResponseEntity.status(500).body("您重复提交了当前的请求！");
                    response.setStatus(500);
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(customObjectMapper.writeValueAsString(body));
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public boolean isRepeatSubmit(HttpServletRequest request, Repeat annotation) throws IOException {
        // 获取参数列表，并序列化
        String nowParams = customObjectMapper.writeValueAsString(request.getParameterMap());

        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        // 获得body的数据

        // 唯一标识（指定key + url + 消息头）
        String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + token + ":" + url;
        // 如果redis中没有存在这个key，说明这个请求是重复提交
        String preParams = redisTemplate.get(cacheRepeatKey);

        if (preParams != null && preParams.equals(nowParams)) {
            return true;
        }
        // 否则我就存入redis，注意设置过期时间
        redisTemplate.set(cacheRepeatKey, nowParams, annotation.value());
        return false;
    }
}
