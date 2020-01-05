package com.cloud.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.cloud.api")
public class WebFluxApplication {

    private static Logger logger = LoggerFactory.getLogger(WebFluxApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(WebFluxApplication.class, args);
        logger.info("WebFluxApplication Application start successfully------------");
    }
}
