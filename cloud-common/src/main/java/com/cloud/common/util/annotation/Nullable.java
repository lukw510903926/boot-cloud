package com.cloud.common.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 非空注解
 *
 * @author : lukewei
 * @project : open-api
 * @createTime : 2018年1月12日 : 下午8:12:31
 * @description :
 * @Inherited 使用注解@Inherited可以让指定的注解在某个类上使用后，这个类的子类将自动被该注解标记。
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Nullable {

    /**
     * 标题
     *
     * @return
     */
    String title() default "";

}
