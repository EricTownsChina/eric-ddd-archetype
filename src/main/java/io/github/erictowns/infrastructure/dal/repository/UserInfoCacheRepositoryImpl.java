package io.github.erictowns.infrastructure.dal.repository;

import io.github.erictowns.domain.user.repository.UserInfoCacheRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Description: 用户信息缓存基础操作
 *
 * @author EricTowns
 * @date 2023/10/10 16:05
 */
@Component
public class UserInfoCacheRepositoryImpl implements UserInfoCacheRepository {

    private static final String KEY_PRE_USERNAME_BY_ID = "username::id::";

    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String getUserName(String userId) {
        if (jedisPool == null || StringUtils.isEmpty(userId)) {
            return null;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String key = KEY_PRE_USERNAME_BY_ID + userId;
            return jedis.get(key);
        }
    }

    @Override
    public void setUserName(String userId, String username) {
        if (jedisPool == null || StringUtils.isEmpty(userId)) {
            return;
        }
        if (StringUtils.isEmpty(username)) {
            username = "";
        }
        try (Jedis jedis = jedisPool.getResource()) {
            String key = KEY_PRE_USERNAME_BY_ID + userId;
            SetParams setParams = new SetParams();
            setParams.ex(3 * 60 * 60L);
            jedis.set(key, username, setParams);
        }
    }

}
