package com.pea.business.sys.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RoleParam {


    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "资源ID")
    private List<Long> resourceId;
    
}