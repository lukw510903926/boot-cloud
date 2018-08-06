package com.tykj.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * restTemplate 调用
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-03 16:44
 **/
@RestController
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/rest/list")
    public List list() {
        return this.restTemplate.getForObject("http://cloud-product/product/dict/type/list", List.class);
    }
}
