package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.oauth.config.auth.dto.LoginDataDetails;
import com.achobeta.www.oauth.exception.LoginMsgConverterException;
import com.alibaba.fastjson2.JSONObject;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthenticationLoginConverter extends ServerFormLoginAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return exchange
                .getRequest()
                .getBody()
                .next()
                .flatMap(body -> {
                    String text = new BufferedReader(new InputStreamReader(body.asInputStream()))
                            .lines().collect(Collectors.joining(System.lineSeparator()));
                    var loginDetails = new LoginDataDetails();
                    try {
                        loginDetails = JSONObject.parseObject(text, LoginDataDetails.class);
                    } catch (Exception e) {
                        return Mono.error(new LoginMsgConverterException(GlobalServiceStatusCode.AUTH_LOGIN_PARAMS_CONVERTER_ERROR));
                    }
                    String username = loginDetails.getUsername();
                    String password = loginDetails.getPassword();

                    username = this.checkStr(username);
                    username = username.trim();
                    password = this.checkStr(password);

                    AuthenticationToken token = new AuthenticationToken(username, password);
                    token.setLoginData(loginDetails);
                    return Mono.just(token);
                });
    }

    private String checkStr(String str) {
        if (StringUtils.isBlank(str)) return StringUtil.EMPTY_STRING;
        return str;
    }

}