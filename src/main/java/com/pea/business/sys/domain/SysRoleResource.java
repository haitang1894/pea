package com.pea.business.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pea.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;


@Data
@TableName(value = "t_sys_role_resource", autoResultMap = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleResource extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "角色资源关联ID")
    private Long id;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "资源ID")
    private Long resourceId;
}