package com.tykj.cloud.producter.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.tykj.cloud.api.api.IDictTypeFeign;
import com.tykj.cloud.api.entity.DictType;

@RestController
public class DictTypeController implements IDictTypeFeign{

	@Override
	public List<DictType> list() {
		
		logger.info("feign request ----------------------------");
		DictType dictType = new DictType();
		dictType.setName("name");
		List<DictType> list = new ArrayList<>();
		list.add(dictType);
		return list;
	}
}
