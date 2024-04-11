package com.pea.common.annotation;

import com.pea.common.enums.BusinessType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogInterface {

	/**
	 * 模块
	 */
	String title() default "";

	/**
	 * 功能
	 */
	BusinessType businessType() default BusinessType.OTHER;

	/**
	 * 是否保存请求的参数
	 */
	boolean isSaveRequestData() default true;

}