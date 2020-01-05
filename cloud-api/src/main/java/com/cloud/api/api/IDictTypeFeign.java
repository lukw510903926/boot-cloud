package com.cloud.api.api;

import com.cloud.api.entity.DictType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author : yangqi
 * @createTime : 2019-06-06 18:07
 * @email : lukewei@mockuai.com
 * @description :
 */
@FeignClient(value = "${cloud.api.product.serviceId}", fallback = DictTypeFallback.class)
public interface IDictTypeFeign {

    @GetMapping("/dict/type/list")
    List<DictType> list();
}
