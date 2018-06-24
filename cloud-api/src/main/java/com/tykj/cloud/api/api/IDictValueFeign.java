package com.tykj.cloud.api.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tykj.cloud.api.entity.DictValue;

@FeignClient("${cloud.api.prodcut.serviceId}")
public interface IDictValueFeign {

	@GetMapping("/dict/value/list")
	List<DictValue> list();
}
