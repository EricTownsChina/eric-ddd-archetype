package io.github.erictowns.infrastructure.dal.config.redis.redis01;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: redis01 jedisPool properties
 *
 * @author EricTowns
 * @date 2023/10/10 15:24
 */
@Component
@ConfigurationProperties(prefix = "datasource.redis.redis01.jedis")
@ConditionalOnProperty(name = "datasource.redis.redis01.enable", havingValue = "true")
public class JedisPoolProperties {

    private Integer maxTotal;
    private Integer maxActive;
    private Integer maxWait;
    private Integer maxIdle;
    private Integer minIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
