package com.achobeta.www.gateway.exception;

import com.achobeta.www.common.util.GlobalServiceStatusCode;

/**
 * <span>
 * Used to throw token parsing exceptions
 * </span>
 *
 * @author jettcc in 2023/10/1
 * @version 1.0
 */

public class TokenParserException extends RuntimeException {
    public TokenParserException(GlobalServiceStatusCode code) {
        super(code.getMessage());
    }
}
