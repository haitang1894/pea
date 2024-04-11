package com.pea.business.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "用户角色VO")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "备注信息")
    private String roleDesc;
}
