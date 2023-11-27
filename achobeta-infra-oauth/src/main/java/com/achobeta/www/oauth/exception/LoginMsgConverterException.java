package com.achobeta.www.oauth.exception;

import com.achobeta.www.common.util.GlobalServiceStatusCode;
import org.springframework.security.core.AuthenticationException;

/**
 * <span>
 *     This exception will be thrown when there is a coding error.
 * {@link com.achobeta.www.oauth.config.auth.AuthenticationLoginConverter}
 * </span>
 * 
 * @author jettcc in 2023/11/4
 * @version 1.0
 */
public class LoginMsgConverterException extends AuthenticationException {
    public LoginMsgConverterException(GlobalServiceStatusCode code) {
        super(code.getMessage());
    }
    public LoginMsgConverterException(GlobalServiceStatusCode code, Throwable e) {
        super(code.getMessage(), e);
    }
}
