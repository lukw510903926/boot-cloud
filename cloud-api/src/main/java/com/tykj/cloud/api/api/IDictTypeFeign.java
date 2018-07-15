package com.tykj.cloud.api.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tykj.cloud.api.entity.DictType;

@FeignClient("${cloud.api.product.serviceId}")
public interface IDictTypeFeign {

	Logger logger = LoggerFactory.getLogger(IDictTypeFeign.class);
	
	@GetMapping("/dict/type/list")
	List<DictType> list();
}
