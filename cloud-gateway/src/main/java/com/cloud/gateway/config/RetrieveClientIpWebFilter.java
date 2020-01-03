package com.cloud.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 获取请求IP
 * @since : 2020-01-03 23:08
 */
@Slf4j
@Component
public class RetrieveClientIpWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        String clientIp = Optional.ofNullable(remoteAddress).map(InetSocketAddress::getAddress).map(InetAddress::getHostAddress).orElse("127.0.0.1");
        log.info("clientIp : {}", clientIp);
        ServerHttpRequest mutatedServerHttpRequest = exchange.getRequest().mutate().header("X-CLIENT-IP", clientIp).build();
        ServerWebExchange mutatedServerWebExchange = exchange.mutate().request(mutatedServerHttpRequest).build();
        return chain.filter(mutatedServerWebExchange);
    }
}
