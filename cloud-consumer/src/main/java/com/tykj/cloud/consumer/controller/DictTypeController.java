package com.tykj.cloud.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;

@RestController
@RequestMapping("/type")
public class DictTypeController {

	@Autowired
	private IDictTypeFeign dictTypeFeign;
	
	@GetMapping("/list")
	public List<DictType> list(){
		
		return this.dictTypeFeign.list();
	}
}
