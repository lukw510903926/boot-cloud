package com.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * 添加自定义请求头
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 **/
@Configuration
public class AuthHttpHeadersFilter implements HttpHeadersFilter {

    private Logger logger = LoggerFactory.getLogger(AuthHttpHeadersFilter.class);

    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        input.add("CLOUD_HEADER", "GATEWAY_CLOUD_HEADER_VALUE");
        logger.info("auth header filter----------------");
        return input;
    }

    @Override
    public boolean supports(Type type) {
        return true;
    }
}
