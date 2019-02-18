package com.tykj.cloud.producter.config;

import com.tykj.cloud.api.filter.HeaderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-11 19:17
 **/
@Configuration
public class ApplicationConfig {

    @Bean
    public FilterRegistrationBean headerFilter() {

        FilterRegistrationBean<HeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HeaderFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("header-filter", "header-filter-value");
        registration.setName("header-filter");
        registration.setOrder(1);
        return registration;
    }

    /*@Bean
    public FilterRegistrationBean authenticationFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setLoginFeign(loginFeign);
        registration.setFilter(authenticationFilter);
        registration.addUrlPatterns("/*");
        registration.setName("authenticationFilter");
        registration.setOrder(2);
        return registration;
    }*/
}
