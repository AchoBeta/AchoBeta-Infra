package com.achobeta.www.oauth.config.handler.logout;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.achobeta.www.oauth.utils.ResponseUtil.createAccessDeniedResponse;
/**
 * <span>
 *     handler logout success logic
 * </span>
 *
 * @author jettcc in 2023/10/23
 * @version 1.0
 */
@Slf4j
@Component
public class AuthenticationLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info(" logout success");
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        return createAccessDeniedResponse(response, GlobalServiceStatusCode.SYSTEM_SUCCESS, "logout success");
    }
}
