package com.achobeta.www.oauth.config.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <span>
 *     login dto
 * </span>
 *
 * @author jettcc in 2023/11/2
 * @version 1.0
 */
@Getter
@ToString
@Setter
public class LoginDataDetails {
    private String username;
    private String password;
}
