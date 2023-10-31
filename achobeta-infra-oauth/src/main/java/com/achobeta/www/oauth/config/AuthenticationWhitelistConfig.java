package com.achobeta.www.oauth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <span>
 * Whitelist request URL list
 * </span>
 *
 * @author jettcc in 2023/10/31
 * @version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "achobeta.auth.whitelist")
public class AuthenticationWhitelistConfig {
    private List<String> urls;
}
