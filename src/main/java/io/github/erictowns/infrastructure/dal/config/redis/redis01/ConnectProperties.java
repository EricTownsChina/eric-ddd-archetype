package io.github.erictowns.infrastructure.dal.config.redis.redis01;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: redis01 connect properties
 *
 * @author EricTowns
 * @date 2023/10/10 15:21
 */
@Component
@ConfigurationProperties(prefix = "datasource.redis.redis01")
@ConditionalOnProperty(name = "datasource.redis.redis01.enable", havingValue = "true")
public class ConnectProperties {

    private Integer database;
    private String host;
    private Integer port;
    private String password;
    private Integer timeout;

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
