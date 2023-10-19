package io.github.erictowns.interfaces.config;

import io.github.erictowns.interfaces.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Description: config to enable xss filter
 *
 * @author EricTowns
 * @date 2023/10/17 21:18
 */
@Configuration
@ConditionalOnProperty(name = "func.xss-filter.enable", havingValue = "true")
public class XssFilterConfig {

    @Bean("xssFilter")
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
