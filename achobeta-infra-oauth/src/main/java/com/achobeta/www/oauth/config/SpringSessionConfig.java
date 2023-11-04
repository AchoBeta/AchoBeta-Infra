package com.achobeta.www.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.SaveMode;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * <span>
 * spring session config
 * </span>
 *
 * @author jettcc in 2023/11/1
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableRedisWebSession(redisNamespace = "achobeta:infra", saveMode = SaveMode.ALWAYS)
public class SpringSessionConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

}
