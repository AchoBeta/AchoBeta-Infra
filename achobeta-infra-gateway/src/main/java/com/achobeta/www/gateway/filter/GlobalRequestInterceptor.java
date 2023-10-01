package com.achobeta.www.gateway.filter;

import com.achobeta.www.gateway.exception.JWSParseException;
import com.achobeta.www.gateway.exception.TokenParserException;
import com.nimbusds.jose.JWSObject;
import io.netty.util.internal.StringUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

import static com.achobeta.www.common.util.GlobalServiceStatusCode.*;

/**
 * <span>
 * <h3>Global request interceptor</h3>
 * The main function of the gateway layer interceptor is to intercept all requests that pass
 * through the gateway and are not in the whitelist,
 * and verify the token carried in the request header (verification includes whether it is empty and correct),
 * and will reject all requests with invalid tokens.
 * </span>
 *
 * @author jettcc in 2023/10/1
 * @version 1.0
 */
@Component
public class GlobalRequestInterceptor implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtil.isNullOrEmpty(token)) {
            return chain.filter(exchange);
        }

        // obtain the token in the request header and parse the data after removing the 'Bearer '
        String userJson;
        try {
            userJson = JWSObject.parse(token.replace("Bearer ", ""))
                    .getPayload().toString();
        } catch (ParseException e) {
            throw new JWSParseException(GATEWAY_JWS_PARSER_ERROR, e);
        }

        if (StringUtil.isNullOrEmpty(userJson)) {
            throw new TokenParserException(GATEWAY_TOKEN_PARSER_ERROR);
        }

        // the parsed user data is placed in the request header and passed to the next module.
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("user", userJson)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
