package io.github.erictowns.infrastructure.dal.repository;

import io.github.erictowns.domain.user.repository.CacheRepository;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Description: 用户信息缓存基础操作
 *
 * @author EricTowns
 * @date 2023/10/10 16:05
 */
@Repository("userInfoCacheRepository")
public class UserInfoCacheRepositoryImpl implements CacheRepository {

    private static final String KEY_PRE_USERNAME_BY_ID = "username::id::";
    private static final long DEFAULT_EXPIRE_SECONDS = 3 * 60 * 60L;

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public @Nullable String get(String key) {
        if (jedisPool == null || StringUtils.isEmpty(key)) {
            return null;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String cacheKey = KEY_PRE_USERNAME_BY_ID + key;
            return jedis.get(cacheKey);
        }
    }

    @Override
    public boolean exists(String key) {
        String username = get(key);
        return username != null;
    }

    @Override
    public void set(String key, String value) {
        set(key, value, DEFAULT_EXPIRE_SECONDS);
    }

    @Override
    public void set(String key, String value, long expireSeconds) {
        if (jedisPool == null || StringUtils.isEmpty(key)) {
            return;
        }
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String cacheKey = KEY_PRE_USERNAME_BY_ID + key;
            SetParams setParams = new SetParams();
            setParams.ex(expireSeconds);
            jedis.set(cacheKey, value, setParams);
        }
    }
}
