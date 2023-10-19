package com.achobeta.www.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;
import static org.springframework.security.config.Customizer.withDefaults;

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
    @Bean
    SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/resources/**","/login", "/signup", "/about")
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
                ).formLogin(withDefaults());
        http.httpBasic();
        // close CSRF
        http.csrf().disable();
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

