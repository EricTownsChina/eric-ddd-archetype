package io.github.erictowns.domain.user.repository;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Description: crud opt for cache
 *
 * @author EricTowns
 * @date 2023/10/19 10:58
 */
public interface CacheRepository {

    /**
     * 缓存get
     *
     * @param key key
     * @return value, nullable
     */
    @Nullable String get(String key);

    /**
     * 缓存是否存在
     *
     * @param key key
     * @return boolean
     */
    boolean exists(String key);

    /**
     * 缓存set
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);

    /**
     * 缓存set
     *
     * @param key           key
     * @param value         value
     * @param expireSeconds 多少秒后缓存失效
     */
    void set(String key, String value, long expireSeconds);

}
