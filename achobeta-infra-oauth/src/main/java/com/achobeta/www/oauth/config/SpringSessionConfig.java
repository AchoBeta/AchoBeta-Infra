package com.achobeta.www.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
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


    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private Integer redisPort = 6379;

    @Value("${spring.data.redis.database:0}")
    private Integer redisDatabase = 0;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(LettuceClientConfiguration configuration) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisHost);
        standaloneConfiguration.setPort(redisPort);
        standaloneConfiguration.setDatabase(redisDatabase);
        standaloneConfiguration.setPassword(redisPassword);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration,configuration);
        connectionFactory.setDatabase(redisDatabase);
        return connectionFactory;
    }

}
