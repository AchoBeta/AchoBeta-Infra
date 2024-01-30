package com.achobeta.www.service.config;

import com.alibaba.fastjson2.support.spring6.http.codec.Fastjson2Decoder;
import com.alibaba.fastjson2.support.spring6.http.codec.Fastjson2Encoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * <span>
 *     use fastjson2 to replace the original serialization and deserialization tools
 * </span>
 * 
 * @author jettcc in 2023/10/1
 * @version 1.0
 */
@Configuration
public class JSONWebMvcConfigurer implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.registerDefaults(false);

        Fastjson2Decoder decoder = new Fastjson2Decoder(new ObjectMapper());
        Fastjson2Encoder encoder = new Fastjson2Encoder(new ObjectMapper());
        configurer.customCodecs().register(decoder);
        configurer.customCodecs().register(encoder);
    }
}
