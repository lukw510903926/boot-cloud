package com.cloud.api.api;

import com.cloud.api.entity.DictValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author : yangqi
 * @createTime : 2019-06-06 18:07
 * @email : lukewei@mockuai.com
 * @description :
 */
@FeignClient("${cloud.api.product.serviceId}")
public interface IDictValueFeign {

    @GetMapping("/dict/value/list")
    List<DictValue> list();
}
