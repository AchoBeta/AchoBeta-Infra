package com.achobeta.www.oauth.config;

import com.alibaba.fastjson2.support.spring6.data.redis.FastJsonRedisSerializer;
import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <span>
 *     redis config
 * </span>
 *
 * @author jettcc in 2023/10/31
 * @version 1.0
 */
@Configuration
public class RedisConfig {
    private static final String[] ACCEPT_TYPES_NAMES = {
            "org.springframework.security.core.context.SecurityContextImpl"
    };

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        GenericFastJsonRedisSerializer serializer =
                new GenericFastJsonRedisSerializer(ACCEPT_TYPES_NAMES);

        // 使用GenericFastJsonRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
    
}