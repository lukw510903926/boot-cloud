package com.tykj.cloud.common.util.annotation;

import java.lang.annotation.*;

/**
 * 非空注解
 * @project : open-api
 * @createTime : 2018年1月12日 : 下午8:12:31
 * @author : lukewei
 * @description :
 *
 * @Inherited 使用注解@Inherited可以让指定的注解在某个类上使用后，这个类的子类将自动被该注解标记。
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Nullable {

	/**
	 * 标题
	 * 
	 * @return
	 */
	String title() default "";

}
