package com.tykj.cloud.consumer.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tykj.cloud.api.api.IDictValueFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;

@Slf4j
@RestController
@RequestMapping("/type")
public class DictTypeController {

	@Autowired
	private IDictTypeFeign dictTypeFeign;

	@Autowired
	private IDictValueFeign dictValueFeign;

	@GetMapping("/list")
	public List<DictType> list(){

		log.info("dictValueFeign : {}",JSONObject.toJSONString(dictValueFeign.list()));
		return this.dictTypeFeign.list();
	}

	@PostMapping("/list")
	public List<DictType> postList(@RequestBody DictType dictType){

		log.info(JSONObject.toJSONString(dictType));
		return this.dictTypeFeign.list();
	}
}
