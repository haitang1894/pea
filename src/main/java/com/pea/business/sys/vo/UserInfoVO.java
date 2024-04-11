package com.pea.business.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户信息VO")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录ID")
    private Long userId;

    @Schema(description = "登录用户名")
    private String userName;

    @Schema(description = "角色")
    private List<String> roles;

}
