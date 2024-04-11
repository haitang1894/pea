package com.pea.business.auth.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserLoginParam {

	@Schema(description = "用户名")
	private String userName;

	@Schema(description = "密码")
	private String password;

	/**
	 * 图形验证码
	 */
	@Schema(description = "验证码")
	private String code;

	/**
	 * 唯一标识
	 */
	@Schema(description = "唯一标识")
	private String uuid = "";

}