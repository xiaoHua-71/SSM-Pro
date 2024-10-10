package com.xh.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.core.RedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-09-30 14:05
 **/
@RestController
@CrossOrigin
@Slf4j
public class TestController {
    @Resource
    private RedisTemplate redisTemplate;

//    @GetMapping("test")
//    public String test(){
//        redisTemplate.setObject("map", List.of("zs","lis","ww"),-1L);
//        List<String> list =  redisTemplate.getObject("map", new TypeReference<>() {});
//        log.info(list.toString());
//        return "hello smm-pro";
//
//    }
}
