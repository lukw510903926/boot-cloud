package com.tykj.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置中心配置信息
 *
 * @author lukw
 * @email 13507615840@163.com
 * @create 2018-07-13 14:05
 **/
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigPropertiesController {

    @Autowired
    private Environment environment;

    @Value("${spring.cloud.config.msg}")
    private String msg;

    @GetMapping("/msg")
    public String msg(){
        return msg;
    }

}
