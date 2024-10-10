package com.xh.configuration;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-08 21:59
 **/
@Configuration
public class ThreadPoolConfiguration {
    //写一个线程池
    @Bean
    public ExecutorService executorService (){
        return new ThreadPoolExecutor(
                10,
                20,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new BasicThreadFactory.Builder().namingPattern("xiaohualog-%d").daemon(true).build(),
                new ThreadPoolExecutor.AbortPolicy());

    }
}
