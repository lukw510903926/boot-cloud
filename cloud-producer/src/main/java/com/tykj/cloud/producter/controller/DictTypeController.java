package com.tykj.cloud.producter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;

@RestController
public class DictTypeController implements IDictTypeFeign {

    @Override
    public List<DictType> list() {

        DictType dictType = new DictType();
        dictType.setName("name:8020");
        List<DictType> list = new ArrayList<>();
        int index = ThreadLocalRandom.current().nextInt(10);
        if (index % 2 == 0) {
            throw new RuntimeException("参数异常");
        }
        list.add(dictType);
        return list;
    }


    @GetMapping("/dict/type")
    @HystrixCommand(fallbackMethod = "typeBack")
    public Object dict() {

        DictType type = new DictType();
        type.setName("HystrixCommand");
        return type;
    }

    public Object typeBack() {

        DictType type = new DictType();
        type.setName("typeBack-HystrixCommand");
        return type;
    }
}
