package com.tykj.cloud.consumer.config;

import com.tykj.cloud.api.filter.HeaderFilter;
import com.tykj.cloud.api.util.Constants;
import feign.RequestInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-11 19:15
 **/
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean headerFilter() {

        FilterRegistrationBean<HeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HeaderFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("header-filter", "header-filter-value");
        registration.setName("header-filter");
        return registration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .maxAge(3600);
    }

    @Bean
    public RequestInterceptor  requestInterceptor(){

        return requestTemplate -> requestTemplate.header(Constants.CLOUD_HEADER,Constants.CLOUD_FEIGN_HEADER+"_VALUE");
    }
}
