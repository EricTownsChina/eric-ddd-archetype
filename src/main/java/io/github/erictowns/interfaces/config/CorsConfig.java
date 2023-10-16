package io.github.erictowns.interfaces.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: deal cors question
 *
 * @author EricTowns
 * @date 2023/10/17 00:14
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE","OPTIONS"};
    private static final int MAX_AGE = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods(ORIGINS)
                .allowCredentials(true)
                .maxAge(MAX_AGE)
                .allowedHeaders("*");
    }

}
