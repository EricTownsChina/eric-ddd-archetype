package io.github.erictowns.interfaces.config;

import io.github.erictowns.domain.user.repository.CacheRepository;
import io.github.erictowns.interfaces.filter.ReplayInvalidateFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: 重放攻击检验配置加载
 *
 * @author EricTowns
 * @date 2023/10/19 15:27
 */
@Configuration
@ConditionalOnProperty(name = "func.replay-invalidate.enable", havingValue = "true")
public class ReplayInvalidateConfig {

    @Resource
    private CacheRepository cacheRepository;
    @Resource
    private ReplayInvalidateProperties replayInvalidateProperties;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        ReplayInvalidateInterceptor replayInvalidateInterceptor =
//                new ReplayInvalidateInterceptor(cacheRepository, replayInvalidateProperties);
//        registry.addInterceptor(replayInvalidateInterceptor);
//    }

    @Bean("replayInvalidateFilter")
    public FilterRegistrationBean<ReplayInvalidateFilter> replayInvalidateFilter() {
        FilterRegistrationBean<ReplayInvalidateFilter> registrationBean = new FilterRegistrationBean<>();
        ReplayInvalidateFilter replayInvalidateFilter =
                new ReplayInvalidateFilter(cacheRepository, replayInvalidateProperties);
        registrationBean.setFilter(replayInvalidateFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Component("replayInvalidateProperties")
    @ConfigurationProperties(prefix = "func.replay-invalidate")
    public static class ReplayInvalidateProperties {
        private long expireSeconds;
        private String token;

        public long getExpireSeconds() {
            return expireSeconds;
        }

        public void setExpireSeconds(long expireSeconds) {
            this.expireSeconds = expireSeconds;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
