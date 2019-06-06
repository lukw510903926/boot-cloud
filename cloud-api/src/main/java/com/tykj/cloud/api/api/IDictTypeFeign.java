package com.tykj.cloud.api.api;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.tykj.cloud.api.entity.DictType;

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
