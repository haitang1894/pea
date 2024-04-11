package com.pea.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: StatusEnums
 * Description: 用户状态
 */
@Getter
@AllArgsConstructor
public enum StatusEnums {

	/**
	 * 禁用
	 */
	DISABLE("2", "禁用"),
	/**
	 * 启用
	 */
	ENABLE("1", "启用"),;

	private final String key;
	
	private final String name;

}
