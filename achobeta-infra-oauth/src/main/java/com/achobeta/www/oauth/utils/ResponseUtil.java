package com.achobeta.www.oauth.utils;

import cn.hutool.json.JSONUtil;
import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.common.util.SystemJsonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <span>
 * authentication response util
 * </span>
 *
 * @author jettcc in 2023/10/23
 * @version 1.0
 */
public class ResponseUtil {
    /**
     * 构造webflux返回时的结构
     */
    public static Mono<Void> createAccessDeniedResponse(ServerHttpResponse resp, GlobalServiceStatusCode code) {
        return createResponse(resp, code, null);
    }

    public static Mono<Void> createAccessDeniedResponse(ServerHttpResponse resp, GlobalServiceStatusCode code, String msg) {
        return createResponse(resp, code, msg);
    }

    private static Mono<Void> createResponse(ServerHttpResponse resp, GlobalServiceStatusCode code, String msg) {
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return resp.writeWith(Mono.just(resp.bufferFactory()
                .wrap(JSONUtil.toJsonStr(buildResponseMessage(code, msg))
                        .getBytes(StandardCharsets.UTF_8))));
    }

    private static SystemJsonResponse buildResponseMessage(GlobalServiceStatusCode code, String msg) {
        if (code == GlobalServiceStatusCode.SYSTEM_SUCCESS) {
            return SystemJsonResponse.SYSTEM_SUCCESS(msg);
        }
        return SystemJsonResponse.CUSTOMIZE_ERROR(code);
    }
}
