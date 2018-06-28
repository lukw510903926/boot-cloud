package com.cloud.gateway.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-06-28 11:27
 **/
public class ServerWebExchangeUtil {

    public static Mono<Void> writeMsg(ServerWebExchange exchange, String msg){

        ServerHttpResponse response = exchange.getResponse();
        exchange.getAttributes().put("original_response_content_type", MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        response.setStatusCode(HttpStatus.FORBIDDEN);
        byte[] bytes = JSONObject.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}
