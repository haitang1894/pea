package com.pea.business.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {

	@Schema(description = "token")
	private String token;

	@Schema(description = "refreshToken")
	private String refreshToken;

}