package com.pea.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 通用返回对象
 */
@Data
public class Result<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "响应状态码", example = "0000")
	private String code;

	@Schema(description = "响应消息")
	private String msg;

	@Schema(description = "响应数据")
	private T data;

	protected Result(String code, String message, T data) {
		this.code = code;
		this.msg = message;
		this.data = data;
	}

	/**
	 * 成功返回结果
	 * @param data 获取的数据
	 */
	public static <T> Result<T> success(T data) {
		return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),
				data);
	}

	/**
	 * 成功返回结果
	 */
	public static <T> Result<T> success() {
		return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),
				null);
	}

	/**
	 * 成功返回结果
	 * @param data 获取的数据
	 * @param message 提示信息
	 */
	public static <T> Result<T> success(T data, String message) {
		return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * 失败返回结果
	 * @param errorCode 错误码
	 */
	public static <T> Result<T> failed(IErrorCode errorCode) {
		return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
	}

	/**
	 * 失败返回结果
	 * @param message 提示信息
	 */
	public static <T> Result<T> failed(String message) {
		return new Result<>(ResultCode.FAILED.getCode(), message, null);
	}

	/**
	 * 失败返回结果
	 * @param message 提示信息
	 */
	public static <T> Result<T> failed(String code, String message) {
		return new Result<>(code, message, null);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> Result<T> failed() {
		return failed(ResultCode.FAILED);
	}

	/**
	 * 参数验证失败返回结果
	 */
	public static <T> Result<T> validateFailed() {
		return failed(ResultCode.VALIDATE_FAILED);
	}

	/**
	 * 参数验证失败返回结果
	 * @param message 提示信息
	 */
	public static <T> Result<T> validateFailed(String message) {
		return new Result<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
	}

	/**
	 * 未登录返回结果
	 */
	public static <T> Result<T> unauthorized(T data) {
		return new Result<>(ResultCode.UNAUTHORIZED.getCode(),
				ResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * 未授权返回结果
	 */
	public static <T> Result<T> forbidden(T data) {
		return new Result<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(),
				data);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("code", code);
		map.put("message", msg);
		map.put("data", data);
		return map;
	}

}
