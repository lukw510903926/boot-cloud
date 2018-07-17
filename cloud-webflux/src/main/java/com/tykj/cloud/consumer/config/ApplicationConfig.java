package com.tykj.cloud.consumer.config;

import com.tykj.cloud.security.autoconfigure.SsoClientProperties;
import com.tykj.cloud.security.util.Constants;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-06-28 12:01
 **/
@Configuration
public class ApplicationConfig {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private SsoClientProperties ssoClientProperties;

    @Bean
    public WebFilter contextPathWebFilter() {
        String contextPath = serverProperties.getServlet().getContextPath();
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (request.getURI().getPath().startsWith(contextPath)) {
                return chain.filter(
                        exchange.mutate()
                                .request(request.mutate().contextPath(contextPath).build())
                                .build());
            }
            return chain.filter(exchange);
        };
    }

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> requestTemplate.header("CLOUD_HEADER",  "CLOUD_FEIGN_HEADER_VALUE")
                .header(Constants.CLOUD_CLIENT_ID, ssoClientProperties.getClientId())
                .header(Constants.CLOUD_CLIENT_KEY, ssoClientProperties.getClientKey())
                .header(Constants.CLOUD_CLIENT_TOKEN, Constants.CLOUD_CLIENT_TOKEN);

    }
}
