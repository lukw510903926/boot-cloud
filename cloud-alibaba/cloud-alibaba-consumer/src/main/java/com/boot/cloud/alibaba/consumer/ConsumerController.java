package com.boot.cloud.alibaba.consumer;

import com.cloud.api.api.IDictValueFeign;
import com.cloud.api.entity.DictValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-07 16:46
 */
@RestController
public class ConsumerController {

    @Resource
    private IDictValueFeign dictValueFeign;

    @GetMapping("/cloud/alibaba/nacos/consumer/list")
    public List<DictValue> list() {
        return dictValueFeign.list();
    }
}
