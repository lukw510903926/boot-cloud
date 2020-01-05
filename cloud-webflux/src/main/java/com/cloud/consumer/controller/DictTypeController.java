package com.cloud.consumer.controller;

import com.cloud.api.api.IDictTypeFeign;
import com.cloud.api.entity.DictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/type")
public class DictTypeController {

    @Autowired
    private IDictTypeFeign dictTypeFeign;

    @GetMapping("/list")
    public Flux<List<DictType>> list() {

        return Flux.just(this.dictTypeFeign.list(), this.dictTypeFeign.list());
    }

    @GetMapping("/mono/list")
    public Mono<List<DictType>> monoList() {

        return Mono.just(this.dictTypeFeign.list());
    }
}
