package com.pea.business.sys.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateUserParam {

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "密码")
    private String password;

}
