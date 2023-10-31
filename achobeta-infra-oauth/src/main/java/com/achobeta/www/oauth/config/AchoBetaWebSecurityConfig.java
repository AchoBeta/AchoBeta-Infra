package com.achobeta.www.oauth.config;

import com.achobeta.www.oauth.config.handler.AuthenticationFailureHandler;
import com.achobeta.www.oauth.config.handler.logout.AuthenticationLogoutHandler;
import com.achobeta.www.oauth.config.handler.logout.AuthenticationLogoutSuccessHandler;
import com.achobeta.www.oauth.config.handler.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

/**
 * <span>
 * security config
 * </span>
 *
 * @author jettcc in 2023/10/18
 * @version 1.0
 */
@Configuration
@EnableWebFluxSecurity
public class AchoBetaWebSecurityConfig {
    @Autowired
    private AuthenticationWhitelistConfig whitelistConfig;
    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        String[] urls = whitelistConfig.getUrls().toArray(new String[0]);
        http
                .authorizeExchange((authorize) -> authorize
                        // 白名单路径
                        .pathMatchers(urls)
                        .permitAll()
                        .pathMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .pathMatchers("/db/**")
                        .access((authentication, context) ->
                                hasRole("ADMIN").check(authentication, context)
                                        .filter(decision -> !decision.isGranted())
                                        .switchIfEmpty(hasRole("DBA").check(authentication, context))
                        )
                        .anyExchange().denyAll()
                ).formLogin(fl ->
                        fl.authenticationSuccessHandler(new AuthenticationSuccessHandler())
                        .authenticationFailureHandler(new AuthenticationFailureHandler()))
                .logout(logoutSpec -> logoutSpec.logoutHandler(new AuthenticationLogoutHandler())
                        .logoutSuccessHandler(new AuthenticationLogoutSuccessHandler())
                )
//                .httpBasic(basicSpec -> {
//                    basicSpec.
//                })
        ;


        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    /**
     * this bean is encryptor
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

