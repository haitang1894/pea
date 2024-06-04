package com.pea.business.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "菜单VO")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenuTreeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "资源ID")
    private Long id;

    @Schema(description = "父节点ID")
    private Integer pId;

    @Schema(description = "路由名称")
    private String label;

    @Schema(description = "children")
    private List<SysMenuTreeVO> children;

}