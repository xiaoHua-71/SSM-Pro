package com.xh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-01 17:09
 **/
//这个工具类可以很轻松的发送请求，他是spring自带的，直接注入容器即可
@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}