package com.pea.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: MenuTypeEnums
 * Description: 资源分类
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnums {

	/**
	 * 菜单
	 */
	MENU("1", "菜单"),
	/**
	 * 目录
	 */
	CATALOGUE("2", "目录"),
	/**
	 * 目录
	 */
	BUTTON("3", "按钮"),
	/**
	 * 基础菜单
	 */
	BASIC_MENU("4", "基础菜单"),;

	private final String code;

	private final String name;

}
