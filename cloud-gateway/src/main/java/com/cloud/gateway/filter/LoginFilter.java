package com.cloud.gateway.filter;

import com.cloud.gateway.util.LoginUser;
import com.cloud.gateway.util.ServerWebExchangeUtil;
import com.cloud.gateway.util.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Description: 登录拦截
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:29:41
 */
public class LoginFilter implements GlobalFilter, Ordered {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TokenManager tokenManager;

	@Autowired
    private void setTokenManager(TokenManager tokenManager){
	    this.tokenManager = tokenManager;
    }

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("login filter------------------------------------");
		List<String> values = exchange.getRequest().getHeaders().getValuesAsList(PermissionFilter.AUTH_HEADER);
		if (CollectionUtils.isEmpty(values)) {
			return ServerWebExchangeUtil.writeMsg(exchange, "401");
		} else {
            LoginUser loginUser = this.tokenManager.getLoginUser(values.get(0));
			if(loginUser == null){
				return ServerWebExchangeUtil.writeMsg(exchange,"401");
			}
		}
		return chain.filter(exchange);
	}
}
