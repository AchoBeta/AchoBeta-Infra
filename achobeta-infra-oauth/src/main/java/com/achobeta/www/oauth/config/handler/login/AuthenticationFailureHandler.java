package com.achobeta.www.oauth.config.handler.login;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.common.util.SystemJsonResponse;
import com.achobeta.www.oauth.exception.LoginMsgConverterException;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import static com.achobeta.www.oauth.utils.ResponseUtil.createAccessDeniedResponse;

/**
 * <span>
 *     handler authentication failure
 * </span>
 *
 * @author jettcc in 2023/10/23
 * @version 1.0
 */
@Component
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            SystemJsonResponse result = SystemJsonResponse.SYSTEM_FAIL();
            // 账号不存在
            if (exception instanceof LoginMsgConverterException) {
                result = SystemJsonResponse.CUSTOMIZE_ERROR(GlobalServiceStatusCode.AUTH_LOGIN_PARAMS_CONVERTER_ERROR);
            }
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes());
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
