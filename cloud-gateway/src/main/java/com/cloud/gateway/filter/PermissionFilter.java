package com.cloud.gateway.filter;

import java.net.URI;
import java.util.List;

import com.cloud.gateway.util.LoginUser;
import com.cloud.gateway.util.ServerWebExchangeUtil;
import com.cloud.gateway.util.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;

/**
 * @author lukew
 * @Description: 权限认证
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:26:45
 */
public class PermissionFilter implements GlobalFilter, Ordered {

    private TokenManager tokenManager;

    @Autowired
    private void setTokenManager(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    public static final String AUTH_HEADER = "gate_auth_header";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
            LoginUser loginUser = this.tokenManager.getLoginUser(values.get(0));
            if (!this.checkPermission(loginUser,requestUri)) {
                return ServerWebExchangeUtil.writeMsg(exchange, "403");
            }
        }
        return chain.filter(exchange);
    }

    private boolean checkPermission(LoginUser loginUser,String requestUrl) {

        List<String> permission = loginUser.getPermission();
        return permission.contains(requestUrl);
    }

}
