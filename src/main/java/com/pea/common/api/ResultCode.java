package com.pea.common.api;

/**
 * 常用API操作码
 */
public enum ResultCode implements IErrorCode {

	/**
	 * 操作成功
	 */
	SUCCESS("0000", "操作成功"),
	FAILED("500", "操作失败"),
	VALIDATE_FAILED("400", "参数检验失败"),
	UNAUTHORIZED("8888", "暂未登录或token已经过期"),
	FORBIDDEN("8889", "暂未登录或token已经过期,没有相关权限"),

	/**
	 * 用户相关
	 */
	ERROR_NAME_REPEAT("8001", "账户名重复"),
	ERROR_USER_NAME_REPEAT("8002", "昵称重复"),

	ERROR_CREATE_USER("8005", "创建用户失败"),

	/**
	 * 资源相关
	 */
	ERROR_RESOURCE_EXISTENCE("8101", "资源不存在"),
	ERROR_PARENT_RESOURCE_DOES_NOT_EXIST("8102", "父资源不存在"),
	ERROR_GET_MENU_PERMISSIONS("8103", "获取菜单权限异常"),
	;
	private final String code;

	private final String message;

	ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}