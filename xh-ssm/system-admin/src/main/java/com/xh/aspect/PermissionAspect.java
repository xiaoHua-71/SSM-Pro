package com.xh.aspect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.annotation.HasPermission;
import com.xh.annotation.HasRole;
import com.xh.constant.Constants;
import com.xh.core.RedisTemplate;
import com.xh.exception.PermissionNeedHasException;
import com.xh.exception.RoleNeedHasException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Permission;
import java.util.List;
import java.util.Objects;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-05 16:02
 **/
@Component
@Aspect
public class PermissionAspect {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 这个该切面执行接口所需角色的权限
     * @param joinPoint
     * @param hasRole
     */

    @Before("@annotation(hasRole)")
    public void roleBefore(JoinPoint joinPoint, HasRole hasRole){
        //获得当前方法所需要的角色
        String[] needRoles = hasRole.value();
        //获得拥有的角色
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 判断有没有Authorization这个请求头，拿到首部的请求头
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
        List<String> hasRoles = redisTemplate.getObject(Constants.ROLE_PREFIX + token, new TypeReference<>() {
        });
        //只要所需的角色有一个在你拥有的角色就放行
        //定义一个flag
        boolean flag = false;
        for (String needRole : needRoles) {
            if(hasRoles.contains(needRole)){
                flag = true;
                break;
            }
        }
        if(!flag) throw new RoleNeedHasException("您没有该接口所需要的角色！");

    }

    @Before("@annotation(hasPermission)")
    public void roleBefore(JoinPoint joinPoint, HasPermission hasPermission){
        //获得当前方法所需要的角色
        String[] needPermissions = hasPermission.value();
        //获得权限
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 判断有没有Authorization这个请求头，拿到首部的请求头
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);
        List<String> hasPermissions = redisTemplate.getObject(Constants.PERM_PREFIX + token, new TypeReference<>() {
        });
        //只要所需的角色有一个在你拥有的角色就放行
        boolean flag = false;
        for (String needPermission : needPermissions) {
            if(hasPermissions.contains(needPermission)){
                flag = true;
                break;
            }
        }
        if(!flag) throw new PermissionNeedHasException("您没有该接口所需要的角色！");

    }


}
