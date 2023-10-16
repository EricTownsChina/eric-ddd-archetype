package io.github.erictowns.infrastructure.dal.config.redis.redis01;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * Description: redis01 configuration
 *
 * @author EricTowns
 * @date 2023/10/10 14:26
 */
@Configuration
@EnableConfigurationProperties({ConnectProperties.class, JedisPoolProperties.class})
@ConditionalOnProperty(name = "datasource.redis.redis01.enable", havingValue = "true")
public class Redis01Config {

    @Bean
    public JedisPool jedisPool(ConnectProperties connectProperties, JedisPoolProperties jedisPoolProperties) {
        // jedisPool conf
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisPoolProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(jedisPoolProperties.getMaxIdle());
        jedisPoolConfig.setTestOnBorrow(jedisPoolProperties.getTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(jedisPoolProperties.getTestOnReturn());
        jedisPoolConfig.setMinIdle(jedisPoolProperties.getMinIdle());
        jedisPoolConfig.setMaxWait(Duration.ofMillis(jedisPoolProperties.getMaxWait()));

        // jedisPool
        return new JedisPool(jedisPoolConfig,
                connectProperties.getHost(),
                connectProperties.getPort(),
                connectProperties.getTimeout(),
                connectProperties.getPassword(),
                connectProperties.getDatabase());
    }

}
