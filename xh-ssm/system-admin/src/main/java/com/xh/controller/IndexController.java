package com.xh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xh.annotation.Log;
import com.xh.entity.XhLoginUser;
import com.xh.entity.XhUser;
import com.xh.service.XhUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-01 14:50
 **/
@Slf4j
@RestController
public class IndexController {

    //引入Service
    @Resource
    private XhUserService userService;

    //前端使用json，后端就需要使用RequestBody接收数据
    @PostMapping("login")
//    @Log(title = "登录操作",businessType = "用户操作")
    public ResponseEntity<XhLoginUser> login(@RequestBody @Validated XhUser xhUser, BindingResult bindingResult){
        //1.处理不合法的数据，通过BindingResult进行收集
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach(error -> log.error("登录时用户校验失败: {}",error.getDefaultMessage()));
        if(allErrors.size() > 0){
            return  ResponseEntity.status(500).build();
        }
        //2.执行登录逻辑
        XhLoginUser xhLoginUser = null;
        try {
            xhLoginUser = userService.login(xhUser.getUserName(),xhUser.getPassword());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        //等到登录用户然后返回
        return ResponseEntity.ok().body(xhLoginUser);
    }

    //登出开始
    @GetMapping("logout")
    public ResponseEntity<String> login(){

        //执行登出逻辑

        try {
            userService.logout();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
        //等到登录用户然后返回
        return ResponseEntity.ok().body("退出成功！");
    }
}
