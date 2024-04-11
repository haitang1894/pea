package com.pea.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: DelStatusEnums
 * Description: 删除状态
 */
@Getter
@AllArgsConstructor
public enum DelStatusEnums {

	/**
	 * 未删除
	 */
	DISABLE(0, "未删除"),
	/**
	 * 已删除
	 */
	ENABLE(1, "已删除"),;

	private final Integer code;

	private final String name;

}
