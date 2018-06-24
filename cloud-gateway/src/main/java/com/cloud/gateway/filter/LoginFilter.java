package com.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @Description: 登录拦截
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:29:41
 */
@Configuration
public class LoginFilter implements GlobalFilter, Ordered {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("login filter------------------------------------");
		return chain.filter(exchange);
	}
}
