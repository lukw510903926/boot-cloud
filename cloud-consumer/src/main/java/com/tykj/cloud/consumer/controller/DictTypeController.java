package com.tykj.cloud.consumer.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;

@RestController
@RequestMapping("/type")
public class DictTypeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IDictTypeFeign dictTypeFeign;

	@GetMapping("/list")
	public List<DictType> list(){

		return this.dictTypeFeign.list();
	}

	@PostMapping("/list")
	public List<DictType> postList(@RequestBody DictType dictType){

		logger.info(JSONObject.toJSONString(dictType));
		return this.dictTypeFeign.list();
	}
}
