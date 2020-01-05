package com.cloud.api.api;

import com.cloud.api.entity.DictType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2019/2/21 17:11
 **/
@Component
public class DictTypeFallback implements IDictTypeFeign {

    @Override
    public List<DictType> list() {

        DictType dictType = new DictType();
        dictType.setName("参数异常:Fallback");
        List<DictType> list = new ArrayList<>();
        list.add(dictType);
        return list;
    }
}
