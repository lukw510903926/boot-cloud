package com.cloud.gateway.filter;

import com.cloud.gateway.util.ServerWebExchangeUtil;
import com.cloud.security.auth.SecurityManager;
import com.cloud.security.util.PermissionUtil;
import com.cloud.security.util.web.LoginUser;
import com.cloud.security.util.web.Permission;
import com.cloud.security.util.web.SystemPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author lukew
 * @Description: 权限认证
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:26:45
 */
public class PermissionFilter implements GlobalFilter, Ordered {

    private SecurityManager securityManager;

    @Autowired
    private void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Autowired
    private ReactiveLoadBalancerClientFilter reactiveLoadBalancerClientFilter;

    public static final String AUTH_HEADER = "gate_auth_header";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int getOrder() {
        return reactiveLoadBalancerClientFilter.getOrder() - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("permission filter-----------------------------------");
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        logger.info("url {}", url);
        String requestUri = exchange.getRequest().getPath().pathWithinApplication().value();
        logger.info(" requestPath : {}", exchange.getRequest().getPath());
        logger.info("requestUri {}", requestUri);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> values = headers.getValuesAsList(AUTH_HEADER);
        if (CollectionUtils.isEmpty(values)) {
            return ServerWebExchangeUtil.writeMsg(exchange, "403");
        } else {
            LoginUser loginUser = this.securityManager.readToken(values.get(0));
            if (!this.hasPermission(loginUser, requestUri, exchange.getRequest().getMethod().name())) {
                return ServerWebExchangeUtil.writeMsg(exchange, "403");
            }
        }
        return chain.filter(exchange);
    }

    private boolean hasPermission(LoginUser loginUser, String requestUrl, String method) {

        SystemPermission systemPermission = securityManager.getSystemPermission();
        Permission permission = PermissionUtil.matchPermission(requestUrl, method, systemPermission.getPermissions());
        if (permission != null) {
            return loginUser.getPermissions().contains(permission);
        }
        return true;
    }

}
