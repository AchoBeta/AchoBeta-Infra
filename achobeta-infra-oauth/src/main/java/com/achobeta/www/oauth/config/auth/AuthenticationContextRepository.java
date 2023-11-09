
package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.oauth.config.auth.dto.LoginDataDetails;
import com.achobeta.www.oauth.config.auth.manager.AuthenticationUsernameManager;
import com.achobeta.www.oauth.utils.KVRedisManger;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * <span>
 * repository,
 * store and load login vouchers in redis
 * </span>
 *
 * @author jettcc in 2023/11/5
 * @version 1.0
 */
@Component
@Slf4j
public class AuthenticationContextRepository
        extends WebSessionServerSecurityContextRepository
        implements ServerSecurityContextRepository {
    private final AuthCommonFunction authCommonFunction;
    private final AuthenticationUsernameManager authenticationUsernameManager;

    public AuthenticationContextRepository(AuthCommonFunction authCommonFunction, AuthenticationUsernameManager authenticationUsernameManager) {
        this.authCommonFunction = authCommonFunction;
        this.authenticationUsernameManager = authenticationUsernameManager;
    }

    /**
     * 使用spring session redis
     * 登陆时会在load方法里把session 存到redis里, 没有拓展的话只需要用继承的就行
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // redis 里有说明不需要验证了
        Authentication authentication = context.getAuthentication();
        AuthenticationToken token = new AuthenticationToken(authentication.getPrincipal(),
                authentication.getCredentials(), authentication.getAuthorities());
        context.setAuthentication(token);
        return super.save(exchange, context);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("{}", exchange.toString());
        // 从header里拿到SESSION ID
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String sessionId = headers.getFirst(HttpHeaders.AUTHORIZATION);
        JSONObject authentication = authCommonFunction.getSessionRedisAuthenticationEntityBySessionId(sessionId);
        if (Objects.isNull(authentication)) {
            return Mono.empty();
        }
        JSONObject principal = JSONObject.from(authentication.get("principal"));
        LoginDataDetails details = new LoginDataDetails();

        details.setId(principal.getLong("id"));
        details.setPhone(principal.getString("phone"));

        details.setUsername(authentication.getString("name"));
        details.setPassword(authentication.getString("credentials"));

        AuthenticationToken token = JSONObject.parseObject(authentication.toString(), AuthenticationToken.class);
        token.setLoginData(details);
        return authenticationUsernameManager
                .authenticate(token)
                .map(SecurityContextImpl::new);
    }
}