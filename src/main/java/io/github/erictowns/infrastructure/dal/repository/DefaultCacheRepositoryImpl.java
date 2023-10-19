package io.github.erictowns.infrastructure.dal.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.erictowns.domain.user.repository.CacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Description: 默认的缓存repository实现, 本地缓存
 *
 * @author EricTowns
 * @date 2023/10/19 11:26
 */
@Primary
@Repository("defaultCacheRepository")
public class DefaultCacheRepositoryImpl implements CacheRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCacheRepositoryImpl.class);
    private static final int MAX_SIZE = 1000;

    private final Cache<String, String> defaultCache = Caffeine.newBuilder()
            .maximumSize(MAX_SIZE)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Override
    public String get(String key) {
        if (null == key) {
            return null;
        }
        return defaultCache.getIfPresent(key);
    }

    @Override
    public boolean exists(String key) {
        String value = get(key);
        return value != null;
    }

    @Override
    public void set(String key, String value) {
        defaultCache.put(key, value);
    }

    @Override
    public void set(String key, String value, long expireSeconds) {
        LOGGER.warn("default cache expire time is 1 min, param expireSeconds: {} is not valid.", expireSeconds);
        defaultCache.put(key, value);
    }

}
