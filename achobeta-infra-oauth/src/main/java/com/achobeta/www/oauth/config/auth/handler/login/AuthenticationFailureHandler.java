package com.achobeta.www.oauth.config.auth.handler.login;

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
 * handler authentication failure
 * </span>
 *
 * @author jettcc in 2023/10/23
 * @version 1.0
 */
@Component
public class AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() ->
                Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
                    var result = switch (exception) {
                        case UsernameNotFoundException ignored -> GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST;
                        case LoginMsgConverterException ignored ->
                                GlobalServiceStatusCode.AUTH_LOGIN_PARAMS_CONVERTER_ERROR;
                        case BadCredentialsException ignored ->
                            GlobalServiceStatusCode.USER_CREDENTIALS_ERROR;
                        // 未知错误抛出异常
                        default -> GlobalServiceStatusCode.SYSTEM_SERVICE_FAIL;
                    };
                    return createAccessDeniedResponse(response, result);
                }));
    }
}
