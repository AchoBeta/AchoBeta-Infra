package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.common.util.SystemJsonResponse;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * <span>
 *     authorized logic processing center
 * </span>
 *
 * @author jettcc in 2023/11/6
 * @version 1.0
 */
@Component
@Slf4j
public class AuthorizationContextManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {

        log.debug("{}", authentication.toString());
        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.debug("{}", path);

        // 配置可访问路径列表, 现在没有需求, 全放开
        List<String> needAuthorityList = new ArrayList<>(){{add("/**");}};

        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(auth -> {
                    log.debug("{}", auth.getAuthorities().toString());
                    return auth.getAuthorities();
                })
                .map(GrantedAuthority::getAuthority)
                .any(needAuthorityList::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(true));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return check(authentication, object)
                .filter(AuthorizationDecision::isGranted)
                .switchIfEmpty(Mono.defer(() -> {
                    String body = JSONObject.toJSONString(SystemJsonResponse.CUSTOMIZE_ERROR(GlobalServiceStatusCode.USER_NO_PERMISSION));
                    return Mono.error(new AccessDeniedException(body));
                }))
                .flatMap(d -> Mono.empty());
    }
}
