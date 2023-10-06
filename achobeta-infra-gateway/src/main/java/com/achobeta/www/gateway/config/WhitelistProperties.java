package com.achobeta.www.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <span>
 *     Whitelist request URL list
 * </span>
 * 
 * @author jettcc in 2023/10/6
 * @version 1.0
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gateway.whitelist")
public class WhitelistProperties {
    List<String> urls;
}
