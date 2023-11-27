package com.achobeta.www.oauth.config.auth;

import com.achobeta.www.oauth.utils.KVRedisManger;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.achobeta.www.oauth.entity.constant.AuthConstant.AUTH_SESSION_ITEM_REDIS_KEY;
import static com.achobeta.www.oauth.entity.constant.AuthConstant.AUTH_SESSION_REDIS_PREFIX;

/**
 * <span>
 *     common function
 * </span>
 *
 * @author jettcc in 2023/11/9
 * @version 1.0
 */
@Component
public class AuthCommonFunction {
    private final KVRedisManger kvRedisManger;

    public AuthCommonFunction(KVRedisManger kvRedisManger) {
        this.kvRedisManger = kvRedisManger;
    }

    public AuthenticationToken getAuthenticationBySessionIdToAuthenticationToken(String sessionId) {
        JSONObject sessionRedisAuthenticationEntity = getSessionRedisAuthenticationEntityBySessionId(sessionId);
        if (Objects.isNull(sessionRedisAuthenticationEntity)) {
            return null;
        }
        return JSONObject.parseObject(sessionRedisAuthenticationEntity.toString(), AuthenticationToken.class);

    }

    public JSONObject getSessionRedisAuthenticationEntityBySessionId(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return null;
        }
        JSONObject auth = JSONObject.from(kvRedisManger.hget(STR. "\{ AUTH_SESSION_REDIS_PREFIX }:\{ sessionId }" , AUTH_SESSION_ITEM_REDIS_KEY));
        if (Objects.isNull(auth)) {
            return null;
        }
        return JSONObject.from(auth.get("authentication"));
    }
}
