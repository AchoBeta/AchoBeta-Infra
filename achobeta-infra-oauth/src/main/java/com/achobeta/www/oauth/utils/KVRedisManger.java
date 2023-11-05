package com.achobeta.www.oauth.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <span>
 * Encapsulate Redis operations
 * </span>
 *
 * @author jettcc in 2023/10/31
 * @version 1.0
 */
@Component
@Slf4j
public class KVRedisManger {
    private static final String TAG = "KVRedisManger";

    private final RedisTemplate<String, Object> kv;

    public KVRedisManger(RedisTemplate<String, Object> kv) {
        this.kv = kv;
    }

    // ============================= Common ============================

    /**
     * specify cache expiration time
     *
     * @param key  key
     * @param time time(second)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                kv.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error(STR."[\{TAG}] >> redis expire error, msg: \{e.getMessage()}");
            return false;
        }
    }

    /**
     * get expiration time
     * @param key key not null
     * @return Time (seconds) Returns 0 for permanent validity
     */
    public long getExpireTime(String key) {
        return Optional.ofNullable(kv.getExpire(key, TimeUnit.SECONDS))
                .orElse(-1L);
    }

    /**
     * determine if the key exists.
     * @param key key
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(kv.hasKey(key));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * delete the specified key.
     * @param key It can be one or more.
     */

    public void del(String... key) {
        if (key != null && key.length > 0) {
            kv.delete(List.of(key));
        }
    }


    /**
     * get normal kv
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        if (StringUtils.isEmpty(key)) return null;
        return kv.opsForValue().get(key);
    }

    // ================================ Map =================================
    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hget(@NonNull String key, @NonNull String item) {
        return kv.opsForHash().get(key, item);
    }

}
