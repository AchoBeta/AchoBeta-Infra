package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.oauth.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <span>
 *     unauthenticated processing handler
 * </span>
 *
 * @author jettcc in 2023/11/6
 * @version 1.0
 */
@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
 
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono
                .defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> ResponseUtil.createAccessDeniedResponse(response, GlobalServiceStatusCode.USER_NO_PERMISSION, "123"));
    }
}
