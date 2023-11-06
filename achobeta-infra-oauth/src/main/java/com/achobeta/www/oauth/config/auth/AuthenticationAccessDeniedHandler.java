package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.oauth.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <span>
 *     authentication error processor
 * </span>
 *
 * @author jettcc in 2023/11/6
 * @version 1.0
 */
@Component
public class AuthenticationAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

        return Mono
                .defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> ResponseUtil.createAccessDeniedResponse(response,
                        GlobalServiceStatusCode.AUTH_LOGIN_PARAMS_CONVERTER_ERROR));

    }
}
