package com.pea.business.sys.vo;

import cn.hutool.json.JSON;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "路由VO")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoutesVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "菜单路由路径")
    private String path;

    @Schema(description = "布局方式")
    private String component;

    @Schema(description = "元数据")
    private JSON meta;

    @Schema(description = "children")
    private List<SysRoutesVO> children;

}
