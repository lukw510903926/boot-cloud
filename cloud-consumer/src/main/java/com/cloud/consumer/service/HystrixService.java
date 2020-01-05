package com.cloud.consumer.service;

import com.cloud.api.entity.DictType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2019/2/21 15:25
 **/
@Service
public class HystrixService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "listBack")
    public Object list() {
        return this.restTemplate.getForObject("http://cloud-product/dict/type/list", Object.class);
    }

    public Object listBack() {

        List<DictType> list = new ArrayList<>();
        DictType type = new DictType();
        type.setName("HystrixCommand");
        list.add(type);
        return list;
    }
}
