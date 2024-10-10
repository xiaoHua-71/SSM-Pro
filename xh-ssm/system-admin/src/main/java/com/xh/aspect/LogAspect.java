package com.xh.aspect;

import com.xh.annotation.Log;
import com.xh.core.RedisTemplate;
import com.xh.entity.XhLoginUser;
import com.xh.entity.XhOperLog;
import com.xh.service.XhOperLogService;
import com.xh.service.XhUserService;
import com.xh.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-07 21:48
 **/
@Aspect
@Component
@Slf4j
public class LogAspect  {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private XhOperLogService xhOperLogService;

//    private BeanFactory beanFactory;
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        this.beanFactory = beanFactory;
//    }


    @AfterReturning("@annotation(log)")
    public void afterReturning(JoinPoint joinPoint, Log log){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        System.out.println("main---" + Thread.currentThread().getName());
        XhOperLog xhOperLog = createOperLog(joinPoint, request, log, null);
        xhOperLogService.insert(xhOperLog);
//        LogAspect logAspect = beanFactory.getBean(this.getClass());
//        XhOperLog xhOperLog = createOperLog(joinPoint, request, log, null);
//        logAspect.logHandler(xhOperLog);


    }

    @AfterThrowing(value = "@annotation(log)",throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Log log,Exception exception){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        System.out.println("main---" + Thread.currentThread().getName());
        XhOperLog xhOperLog = createOperLog(joinPoint, request, log, exception);
        //这个方法是异步的
        xhOperLogService.insert(xhOperLog);


    }

//    @Async("xh-logger")
//    public void logHandler(XhOperLog xhOperLog) {
//
//        xhOperLogService.insert(xhOperLog);
//    }

    private XhOperLog createOperLog(JoinPoint joinPoint, HttpServletRequest request, Log log, Exception exception) {
        System.out.println("log----" + Thread.currentThread().getName());
        //1.根据线程信息，封装日志实例
        XhOperLog xhOperLog = new XhOperLog();
        xhOperLog.setTitle(log.title());
        xhOperLog.setBusinessType(log.businessType());
        if(exception != null){
            xhOperLog.setErrormsg(exception.getMessage().length() >1000?
                    exception.getMessage().substring(0,1000): exception.getMessage());
            xhOperLog.setStatus(500);
        }else {
            xhOperLog.setStatus(200);
        }
        //request当中获取的信息
        xhOperLog.setOperIp(request.getRemoteUser());
        xhOperLog.setRequestMethod(request.getMethod());

        //操作人
        //这里面会有些问题  空指针的问题

        // 获取操作人信息
        // 确保获取用户信息时不会抛出异常
        try {
            XhLoginUser loginUser = AuthUtil.getLoginUser(redisTemplate);
            if (loginUser != null && loginUser.getXhUser() != null) {
                xhOperLog.setOperName(loginUser.getXhUser().getUserName());
            } else {
                xhOperLog.setOperName("未登录用户"); // 如果用户未登录
            }
        } catch (Exception e) {
            // 如果获取用户时发生错误，记录错误信息
            xhOperLog.setOperName("未登录用户"); // 默认值
            xhOperLog.setErrormsg("获取用户信息失败: " + e.getMessage());
        }


        xhOperLog.setOperUrl(request.getRequestURI());
        //获取执行的方法
        String methodName = joinPoint.getSignature().getName();

        xhOperLog.setMethod(methodName);
        xhOperLog.setOpertime(new Date());
        return xhOperLog;
    }


}
