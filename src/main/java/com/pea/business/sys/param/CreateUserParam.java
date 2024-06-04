package com.pea.business.sys.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserParam {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "性别")
    private String userGender;

    @Schema(description = "状态：1-可用，2-禁用")
    private String status;

    @Schema(description = "电话")
    private String userPhone;

    @Schema(description = "电子邮箱")
    private String userEmail;

    @Schema(description = "角色信息")
    private List<String> userRoles;

}
