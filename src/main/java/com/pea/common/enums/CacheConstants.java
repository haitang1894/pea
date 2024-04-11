package com.pea.common.enums;

/**
 * 缓存的key 常量
 */
public interface CacheConstants {

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

	/**
	 * 菜单信息缓存
	 */
	String MENU_DETAILS = "menu_details";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "user_details";

	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS = "dict_details";

	/**
	 * 参数缓存
	 */
	String PARAMS_DETAILS = "params_details";

	/**
	 * 手机验证码是否存在标记
	 */
	String SMS_CODE_EXIST = "sms_code_exist:";

}
