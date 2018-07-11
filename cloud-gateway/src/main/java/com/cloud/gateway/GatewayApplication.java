package com.cloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

	private static Logger logger = LoggerFactory.getLogger(GatewayApplication.class);
	
	public static void main(String[] args){
		SpringApplication.run(GatewayApplication.class, args);
		logger.info("gateway application start successfully---------");
	}
//
//	@Bean
//	@Primary
//	public NettyRoutingFilter nettyRoutingFilter(HttpClient httpClient,
//												 ObjectProvider<List<HttpHeadersFilter>> headersFilters) {
//		List<HttpHeadersFilter> list = headersFilters.getIfAvailable();
//		HttpHeadersFilter httpHeadersFilter = new HttpHeadersFilter() {
//			@Override
//			public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
//				input.add("CLOUD_HEADER","GATEWAY_CLOUD_HEADER_VALUE");
//				return input;
//			}
//		};
//
//		httpHeadersFilter.supports(HttpHeadersFilter.Type.REQUEST);
//		list.add(httpHeadersFilter);
//		return new NettyRoutingFilter(httpClient, headersFilters);
//	}
	
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
