package com.boot.cloud.alibaba.producer;

import com.cloud.api.api.IDictValueFeign;
import com.cloud.api.entity.DictValue;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-06-07 16:37
 */
@RestController
public class ProducerController implements IDictValueFeign {

    @Override
    public List<DictValue> list() {
        DictValue dictValue = new DictValue();
        dictValue.setName("value");
        dictValue.setDictTypeId("typeId");
        return Arrays.asList(dictValue);
    }
}
