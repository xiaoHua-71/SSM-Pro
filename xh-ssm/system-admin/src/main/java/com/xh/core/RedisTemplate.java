package com.xh.core;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectJoin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xh.configuration.CustomObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-09-30 21:05
 **/
@Component
@Slf4j
public class RedisTemplate {
    //注入一个jedis连接池
    @Resource
    private JedisPool jedisPool;

    //序列化，反序列化
    @Resource
    private CustomObjectMapper objectMapper;

    //保存字符串类型的数据
    public String set(String key,String value,Long expire){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;

        try {
            //访问成功，拿到数据，否则抛出异常
            returnValue = jedis.setex(key,expire,value);
        }catch (JedisException jedisException){
            log.error("redis is execution error",jedisException);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }

        return returnValue;
    }
    //获取数据
    public String get(String key){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;

        try {
            //访问成功，拿到数据，否则抛出异常
            returnValue = jedis.get(key);
        }catch (JedisException jedisException){
            log.error("redis is execution error",jedisException);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }

        return returnValue;
    }

    //将对象以序列化的方式存在redis，json
    public String setObject(String key, Object value, Long expire){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        String returnValue = null;

        try {
            //先序列化成json
            String jsonValue = objectMapper.writeValueAsString(value);
            if (expire > 0) {
                return jedis.setex(key, expire,  jsonValue);
            } else {
                return jedis.set(key, jsonValue);
            }

        }catch (JedisException | JsonProcessingException jedisException){
            log.error("redis is execution error",jedisException);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }

        return returnValue;
    }


    //将对象以序列化的方式存在redis，json
    public <T> T getObject(String key, TypeReference<T> typeReference){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        T object = null;

        try {
            //从redis中获取
            String returnValue = jedis.get(key);

            object = objectMapper.readValue(returnValue, typeReference);
        }catch (JedisException | JsonProcessingException jedisException){
            log.error("redis is execution error",jedisException);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }

        return object;
    }

    //删除的方法，用于账号登出
    public Long remove(String ...key){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        //初始化del
        long del = 0L;
        try {
            //删除数据
            del = jedis.del(key);

        }catch (JedisException jedisException){
            log.error("redis is execution error",jedisException);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }

        return del;
    }

    //token续命，防止在登录过程中token过期
    public long expire(String key,Long expire){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        long exp = -1L;

        try {
            //从redis中获取
            exp = jedis.expire(key, expire);
        }catch (JedisException e ){
            log.error("redis is execution error",e);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }
        return exp;
    }

    //查询token用来判断用户是否登录
    public  Set<String> keys(String pattern){
        //拿到连接池
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = null;

        try {
            //从redis中获取

            keys = jedis.keys(pattern);
        }catch (JedisException e ){
            log.error("redis is execution error",e);
            jedisPool.returnBrokenResource(jedis);
        }finally {
            //返还资源
            jedisPool.returnResource(jedis);
        }


        return keys;
    }



}
