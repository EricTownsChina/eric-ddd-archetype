package io.github.erictowns.interfaces.config;

import io.github.erictowns.interfaces.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: config to enable xss filter
 *
 * @author EricTowns
 * @date 2023/10/17 21:18
 */
@Configuration
@ConditionalOnProperty(name = "func.xss-filter.enable", havingValue = "true")
public class XssFilterConfig {

    @Resource
    private XssFilterProperties xssFilterProperties;

    @Bean("xssFilter")
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns(xssFilterProperties.getUrlPatterns());
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Component("xssFilterProperties")
    @ConfigurationProperties(prefix = "func.xss-filter")
    public static class XssFilterProperties {
        private String[] urlPatterns = {"/*"};

        public String[] getUrlPatterns() {
            return urlPatterns;
        }

        public void setUrlPatterns(String[] urlPatterns) {
            this.urlPatterns = urlPatterns;
        }
    }

}
