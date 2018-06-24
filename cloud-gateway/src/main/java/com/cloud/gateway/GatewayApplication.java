package com.cloud.gateway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cloud.gateway.filter.NettyRoutingFilter;

import reactor.ipc.netty.http.client.HttpClient;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

	private static Logger logger = LoggerFactory.getLogger(GatewayApplication.class);
	
	public static void main(String[] args){
		SpringApplication.run(GatewayApplication.class, args);
		logger.info("gateway application start successfully---------");
	}
	
	@Bean
	@Primary
	public NettyRoutingFilter nettyRoutingFilter(HttpClient httpClient,
											ObjectProvider<List<HttpHeadersFilter>> headersFilters) {
		return new NettyRoutingFilter(httpClient, headersFilters);
	}
	
	@Bean
	public HttpMessageConverters messageConverters() {

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return new HttpMessageConverters(fastConverter);
	}
}
