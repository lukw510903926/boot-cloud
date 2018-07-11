package com.tykj.cloud.consumer.config;

import com.tykj.cloud.api.filter.HeaderFilter;
import com.tykj.cloud.api.util.Constants;
import feign.RequestInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-11 19:15
 **/
@Configuration
public class ApplicationConfig {

    @Bean
    public FilterRegistrationBean headerFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HeaderFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("header-filter", "header-filter-value");
        registration.setName("header-filter");
        return registration;
    }

    @Bean
    public RequestInterceptor  requestInterceptor(){

        return requestTemplate -> requestTemplate.header(Constants.CLOUD_HEADER,Constants.CLOUD_FEIGN_HEADER+"_VALUE");
    }
}
