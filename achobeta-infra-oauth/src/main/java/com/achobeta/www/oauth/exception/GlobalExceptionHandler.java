package com.achobeta.www.oauth.exception;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import com.achobeta.www.common.util.SystemJsonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * <span>
 * Handle all exceptions thrown by the controller layer.
 * </span>
 *
 * @author jettcc in 2023/11/4
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 处理所有不可知的异常
     *
     * @param e 异常对象
     * @return {@link com.achobeta.www.common.util.SystemJsonResponse}
     */
    @ExceptionHandler(Exception.class)
    Mono<SystemJsonResponse> handleException(Exception e) {
        return Mono.just(
                SystemJsonResponse.CUSTOMIZE_MSG_ERROR(GlobalServiceStatusCode.SYSTEM_SERVICE_FAIL, "异常信息:" + e.getMessage())
        );
    }
}