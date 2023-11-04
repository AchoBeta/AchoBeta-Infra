package com.achobeta.www.oauth.config.handler;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
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
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        return createAccessDeniedResponse(response, GlobalServiceStatusCode.USER_NO_PERMISSION);
    }
}
