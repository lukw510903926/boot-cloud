package com.cloud.common.util;

import com.cloud.common.util.annotation.Nullable;
import com.cloud.common.util.exception.NullableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 非空校验工具类
 *
 * @author : lukewei
 * @project : open-api
 * @createTime : 2018年1月12日 : 下午8:12:09
 * @description :
 */
@Slf4j
public class NullableValidator {

    /**
     * 非空校验
     *
     * @param object
     */
    public static void validate(Object object) {

        Object value;
        Nullable nullable;
        boolean flag = true;
        log.info("validate object : {}", object);
        StringBuilder msg = new StringBuilder();
        Nullable annotation = object.getClass().getAnnotation(Nullable.class);
        if (annotation != null) {
            msg.append(annotation.title()).append(":");
        }
        List<Field> list = ReflectionUtils.getFields(object);
        for (Field field : list) {
            if (field.isAnnotationPresent(Nullable.class)) {
                value = ReflectionUtils.getter(object, field);
                nullable = field.getAnnotation(Nullable.class);
                if (value == null) {
                    flag = false;
                    msg.append("【").append(nullable.title()).append("】不可为空,");
                }
                if (flag) {
                    if (String.class.isAssignableFrom(value.getClass()) && StringUtils.isBlank((String) value)) {
                        flag = false;
                        msg.append("【").append(nullable.title()).append("】不可为空,");
                    }
                    if (Collection.class.isAssignableFrom(value.getClass()) && CollectionUtils.isEmpty((Collection<?>) value)) {
                        flag = false;
                        msg.append("【").append(nullable.title()).append("】不可为空,");
                    }
                    if (Map.class.isAssignableFrom(value.getClass()) && MapUtils.isEmpty((Map<?, ?>) value)) {
                        flag = false;
                        msg.append("【").append(nullable.title()).append("】不可为空,");
                    }
                }
            }
        }
        if (!flag) {
            log.info("msg : {}", msg.toString());
            throw new NullableException(msg.toString());
        }
    }
}
