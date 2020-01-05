package com.cloud.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.api.api.IDictTypeFeign;
import com.cloud.api.api.IDictValueFeign;
import com.cloud.api.entity.DictType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/type")
public class DictTypeController {

    @Autowired
    private IDictTypeFeign dictTypeFeign;

    @Autowired
    private IDictValueFeign dictValueFeign;

    @GetMapping("/list")
    public List<DictType> list() {

        log.info("dictValueFeign : {}", JSONObject.toJSONString(dictValueFeign.list()));
        return this.dictTypeFeign.list();
    }

    @PostMapping("/list")
    public List<DictType> postList(@RequestBody DictType dictType) {

        log.info(JSONObject.toJSONString(dictType));
        return this.dictTypeFeign.list();
    }
}
