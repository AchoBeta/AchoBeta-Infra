package com.achobeta.www.gateway.exception;

import com.achobeta.www.common.util.GlobalServiceStatusCode;

/**
 * <span>
 * Used to throw jws parsing exceptions
 * </span>
 *
 * @author jettcc in 2023/10/1
 * @version 1.0
 */
public class JWSParseException extends RuntimeException {
    public JWSParseException(GlobalServiceStatusCode code, Throwable cause) {
        super(code.getMessage(), cause);
    }
}

