package com.boot.cloud.alibaba.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-06 22:11
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class AlibabaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaProducerApplication.class, args);
    }
}