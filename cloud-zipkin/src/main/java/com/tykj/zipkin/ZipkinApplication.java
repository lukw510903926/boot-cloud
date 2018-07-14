package com.tykj.zipkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;

/**
 * 日志采集 服务监控
 * Created with IntelliJ IDEA.
 * Description:
 * @author : lukew
 * Date: 2017-11-14:11:42
 */
@EnableEurekaClient
@EnableZipkinServer
@SpringBootApplication
public class ZipkinApplication {

    private static Logger logger = LoggerFactory.getLogger(ZipkinApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ZipkinApplication.class,args);
        logger.info(" zipkin application start successfully ");
    }
}
