package com.achobeta.www.oauth.config.manager;

import com.achobeta.www.oauth.security.AchobetaUserDetailsServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        // 转换为自定义security令牌
        return retrieveUser(username)
                .publishOn(scheduler)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("账号或密码错误！"))))
                .map(u-> new UsernamePasswordAuthenticationToken(u, u.getPassword()));
    }
    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return achobetaUserDetailsService.findByUsername(username);
    }


}
