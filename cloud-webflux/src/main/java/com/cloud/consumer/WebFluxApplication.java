package com.cloud.consumer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableEurekaClient
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.cloud.api")
public class WebFluxApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebFluxApplication.class, args);
        log.info("WebFluxApplication Application start successfully------------");
    }
}
