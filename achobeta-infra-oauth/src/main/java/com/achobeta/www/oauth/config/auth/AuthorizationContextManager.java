package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.common.util.SystemJsonResponse;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

/**
 * <span>
 * authorized logic processing center
 * </span>
 *
 * @author jettcc in 2023/11/6
 * @version 1.0
 */
@Component
@Slf4j
public class AuthorizationContextManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final AuthCommonFunction authCommonFunction;

    public AuthorizationContextManager(AuthCommonFunction authCommonFunction) {
        this.authCommonFunction = authCommonFunction;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        /*
            配置可访问路径列表, 现在没有需求, 全放开, 这里只是留个模板以后好改
            List<String> needAuthorityList = new ArrayList<>(){{add("/**");}};
            AuthorizationDecision authorizationDecision = new AuthorizationDecision(true);
         */
        HttpHeaders headers = context.getExchange().getRequest().getHeaders();
        Authentication auth = authCommonFunction.getAuthenticationBySessionIdToAuthenticationToken(headers.getFirst(HttpHeaders.AUTHORIZATION));

        return  Mono.justOrEmpty(auth)
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(s -> true)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        log.debug("verify: {}", authentication.toString());
        return check(authentication, object)
                .filter(AuthorizationDecision::isGranted)
                .switchIfEmpty(Mono.defer(() -> {
                    String body = JSONObject.toJSONString(SystemJsonResponse.CUSTOMIZE_ERROR(GlobalServiceStatusCode.USER_NO_PERMISSION));
                    return Mono.error(new AccessDeniedException(body));
                }))
                .flatMap(d -> Mono.empty());
    }
}
