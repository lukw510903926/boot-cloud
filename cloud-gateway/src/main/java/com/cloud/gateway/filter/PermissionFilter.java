package com.cloud.gateway.filter;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @Description: 权限拦截
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年6月23日 下午4:26:45
 */
@Configuration
public class PermissionFilter implements GlobalFilter, Ordered {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GatewayProperties gatewayProperties;

	public static  final String AUTH_HEADER = "gate_auth_header";

	@Override
	public int getOrder() {
		return LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER - 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("permission filter-----------------------------------");
		URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
		String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
		if (url == null || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
			return chain.filter(exchange);
		}
		addOriginalRequestUrl(exchange, url);
		String requestUrl = url.toString();
		List<RouteDefinition> routes = gatewayProperties.getRoutes();
		// routes [RouteDefinition{id='cloud-product',
		// predicates=[PredicateDefinition{name='Path', args={_genkey_0=/product/**}}],
		// filters=[], uri=lb://cloud-product, order=8000}]
		logger.info(" routes {}", routes);
		RouteDefinition route = null;
		for (RouteDefinition routeDefinition : routes) {
			if (requestUrl.startsWith(routeDefinition.getUri().toString())) {
				route = routeDefinition;
				break;
			}
		}
		if (route != null) {
			logger.info(" route {}", route);
			URI uri = exchange.getRequest().getURI();
			String port = exchange.getRequest().getURI().getPort() + "";
			requestUrl = requestUrl.substring(requestUrl.indexOf(port) + port.length());
			logger.info("uri : {}", uri);
			logger.info("requestURL : {}", requestUrl);
			HttpHeaders headers = exchange.getRequest().getHeaders();
			List<String> values = headers.getValuesAsList(AUTH_HEADER);
			if(CollectionUtils.isEmpty(values)){
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.FORBIDDEN);
				return null;
			}

		}
		return chain.filter(exchange);
	}
}
