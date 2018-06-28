package com.tykj.cloud.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/type")
public class DictTypeController {

	@Autowired
	private IDictTypeFeign dictTypeFeign;
	
	@GetMapping("/list")
	public Flux<List<DictType>> list(){
		
		return Flux.just(this.dictTypeFeign.list(),this.dictTypeFeign.list());
	}

	@GetMapping("/mono/list")
	public Mono<List<DictType>> monoList(){

		return Mono.just(this.dictTypeFeign.list());
	}
}
