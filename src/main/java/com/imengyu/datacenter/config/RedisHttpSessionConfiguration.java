package com.imengyu.datacenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启Redis Http Session
 */
@Configuration
@EnableRedisHttpSession
public class RedisHttpSessionConfiguration {

}