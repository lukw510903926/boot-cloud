package com.cloud.gateway.filter;

import java.net.URI;
import java.util.List;
import com.cloud.gateway.util.ServerWebExchangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;

/**
 * @author lukew
 * @Description: 权限拦截
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:26:45
 */
@Configuration
public class PermissionFilter implements GlobalFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String AUTH_HEADER = "gate_auth_header";

    @Override
    public int getOrder() {
        return LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("permission filter-----------------------------------");
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        logger.info("url {}",url);
        String requestUri = exchange.getRequest().getPath().pathWithinApplication().value();
        logger.info(" requestPath : {}",exchange.getRequest().getPath());
        logger.info("requestUri {}",requestUri);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> values = headers.getValuesAsList(AUTH_HEADER);
        if (CollectionUtils.isEmpty(values)) {
            return ServerWebExchangeUtil.writeMsg(exchange, "403");
        } else {
            Object loginUser = this.getLoginUser(values.get(0));
            if (!this.checkPermission(loginUser)) {
                return ServerWebExchangeUtil.writeMsg(exchange, "403");
            }
        }
        return chain.filter(exchange);
    }

    private boolean checkPermission(Object loginUser) {

        return loginUser == null;
    }

    private Object getLoginUser(String token) {

        return token;
    }
}
