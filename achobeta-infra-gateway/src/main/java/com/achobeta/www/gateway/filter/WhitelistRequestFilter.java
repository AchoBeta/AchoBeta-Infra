package com.achobeta.www.gateway.filter;

import com.achobeta.www.gateway.config.WhitelistProperties;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.achobeta.www.gateway.utils.GatewayConstants.AUTHORIZATION_HEADER;
import static com.achobeta.www.gateway.utils.GatewayConstants.REQUEST_TYPE_GET;
/**
 * <span>
 *     Whitelist filter
 * </span>
 * 
 * @author jettcc in 2023/10/6
 * @version 1.0
 */
@Configuration
@Slf4j
public class WhitelistRequestFilter implements WebFilter {
    private final WhitelistProperties whitelist;

    public WhitelistRequestFilter(WhitelistProperties whitelist) {
        this.whitelist = whitelist;
    }

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var path = request.getURI().getPath();
        var urls = whitelist.getUrls();
        var requestMethod = request.getMethod().name();
        var pathMatcher = new AntPathMatcher();
        // whitelist that only allows GET requests
        if (!REQUEST_TYPE_GET.equalsIgnoreCase(requestMethod)) return chain.filter(exchange);
        for (var u : urls) {
            if (pathMatcher.match(u, path)) {
                request = exchange.getRequest()
                        .mutate().header(AUTHORIZATION_HEADER, "")
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }
        }

        return chain.filter(exchange);
    }
}
