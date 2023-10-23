package com.achobeta.www.oauth.config.handler;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import static com.achobeta.www.oauth.utils.ResponseUtil.createAccessDeniedResponse;

/**
 * <span>
 *     authentication success handler
 * </span>
 *
 * @author jettcc in 2023/10/23
 * @version 1.0
 */
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        return createAccessDeniedResponse(response, GlobalServiceStatusCode.SYSTEM_SUCCESS);
    }

}
