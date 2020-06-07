package com.boot.cloud.alibaba.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-06 22:11
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.cloud.api")
public class AlibabaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaConsumerApplication.class, args);
    }
}
