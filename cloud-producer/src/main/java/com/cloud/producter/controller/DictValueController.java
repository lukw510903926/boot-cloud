package com.cloud.producter.controller;

import com.cloud.api.api.IDictValueFeign;
import com.cloud.api.entity.DictValue;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author : yangqi
 * @createTime : 2019-06-06 18:08
 * @email : lukewei@mockuai.com
 * @description :
 */
@RestController
public class DictValueController implements IDictValueFeign {

    @Override
    public List<DictValue> list() {

        DictValue dictValue = new DictValue();
        dictValue.setName("value");
        dictValue.setDictTypeId("typeId");
        return Arrays.asList(dictValue);
    }
}
