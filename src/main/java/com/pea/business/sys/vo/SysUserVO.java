package com.pea.business.sys.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pea.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户视图VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserVO extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "登录用户名")
    private String userName;

    @Schema(description = "性别")
    private String userGender;

    @Schema(description = "状态：1-可用，2-禁用", example = "1")
    private String status;

    @Schema(description = "电话")
    private String userPhone;

    @Schema(description = "电子邮箱")
    private String userEmail;

    @Schema(description = "最后登录时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    @Schema(description = "用户角色")
    private List<String> userRoles;
}