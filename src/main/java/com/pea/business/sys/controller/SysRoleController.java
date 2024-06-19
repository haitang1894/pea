package com.pea.business.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pea.business.sys.domain.SysRole;
import com.pea.business.sys.param.RoleParam;
import com.pea.business.sys.service.SysRoleService;
import com.pea.business.sys.vo.SysRoleVO;
import com.pea.common.annotation.SysLogInterface;
import com.pea.common.api.Result;
import com.pea.common.enums.BusinessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SysRoleController", description = "用户角色控制层")
//@RequestMapping("/sys/role")
@RequestMapping("/systemManage")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Operation(summary = "list 分页列表")
    @Parameters({
            @Parameter(name = "current", description = "当前页", required = true, example = "1"),
            @Parameter(name = "size", description = "每页显示条数", required = true, example = "10"),
            @Parameter(name = "username", description = "用户名称"),
    })
    @GetMapping(value = "/getRoleList")
    @SysLogInterface(title = "查询角色信息", businessType = BusinessType.OTHER)
    public Result<IPage<SysRole>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        IPage<SysRole> sysUsers = sysRoleService.getPage(params);
        return Result.success(sysUsers);
    }

    @Operation(summary = "查询角色信息")
    @GetMapping(value = "/getAllRoles")
    public Result<List<SysRoleVO>> getAllRoles(@RequestHeader("Authorization") String authorizationHeader) {
        return sysRoleService.getAllRoles(authorizationHeader);
    }

    @Operation(summary = "新增角色信息")
    @PostMapping(value = "/addRole")
    public Result<Boolean> add(@RequestBody SysRole sysRole) {
        return sysRoleService.add(sysRole);
    }

    @Operation(summary = "查询角色资源ID")
    @PostMapping(value = "/getRoleResourceId")
    public Result<List<Long>> getRoleResourceId(@RequestBody Long roleId) {
        return sysRoleService.getRoleResourceId(roleId);
    }

    @Operation(summary = "修改角色资源信息")
    @PostMapping(value = "/updateRoleResourceInfo")
    public Result<Boolean> updateRoleResourceInfo(@RequestBody RoleParam roleParam) {
        return sysRoleService.updateRoleResourceInfo(roleParam.getRoleId(), roleParam.getResourceId());
    }

}
