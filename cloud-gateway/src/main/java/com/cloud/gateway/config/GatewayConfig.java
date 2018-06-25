package com.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;
import java.util.Arrays;
import java.util.List;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-06-25 11:56
 **/
@Configuration
public class GatewayConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        List<String> list = Arrays.asList(new String[]{"GET", "POST", "DELETE", "PUT", "OPTIONS"});
        config.setAllowedMethods(list);
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        PathPatternParser patternParser = new PathPatternParser();
        patternParser.parse("/**");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(patternParser);
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
