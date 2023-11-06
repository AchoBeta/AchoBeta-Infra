package com.achobeta.www.oauth.config.auth.manager;

import com.achobeta.www.oauth.config.auth.AuthenticationToken;
import com.achobeta.www.oauth.config.auth.dto.LoginDataDetails;
import com.achobeta.www.oauth.security.AchobetaUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

/**
 * <span>
 *     when using username-password login authentication,
 *     this manager will be used.
 * </span>
 * 
 * @author jettcc in 2023/11/4
 * @version 1.0
 */
@Component
@Primary
@Slf4j
public class AuthenticationUsernameManager extends AbstractUserDetailsReactiveAuthenticationManager {
    private final Scheduler scheduler = Schedulers.boundedElastic();
    private final AchobetaUserDetailsServiceImpl achobetaUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationUsernameManager(AchobetaUserDetailsServiceImpl achobetaUserDetailsService, PasswordEncoder passwordEncoder) {
        this.achobetaUserDetailsService = achobetaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 已经通过验证，直接返回
        if (authentication.isAuthenticated()) {
            return Mono.just(authentication);
        }

        AuthenticationToken token = (AuthenticationToken) authentication;
        log.debug("{}", token);

        LoginDataDetails loginData = token.getLoginData();
        if (Objects.isNull(loginData)) {
            throw new AuthenticationServiceException("未获取到登陆参数");
        }

        return retrieveUser(loginData.getUsername())
                .publishOn(scheduler)
                .filter(user -> passwordEncoder.matches(loginData.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("账号或密码错误！"))))
                .map(u-> new UsernamePasswordAuthenticationToken(u, u.getPassword()))
        ;
    }

    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return achobetaUserDetailsService.findByUsername(username);
    }


}
