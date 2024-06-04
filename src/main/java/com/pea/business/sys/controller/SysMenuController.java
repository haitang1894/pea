package com.pea.business.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pea.business.sys.service.SysResourceService;
import com.pea.business.sys.vo.SysMenuTreeVO;
import com.pea.business.sys.vo.SysMenuVO;
import com.pea.common.api.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SysMenuController", description = "菜单信息控制层")
//@RequestMapping("/sys/menu")
@RequestMapping("/systemManage")
public class SysMenuController {

    private final SysResourceService sysResourceService;

    @Operation(summary = "查询菜单信息")
    @GetMapping(value = "/getMenuList/v2")
    @Parameters({
            @Parameter(name = "current", description = "当前页", required = true, example = "1"),
            @Parameter(name = "size", description = "每页显示条数", required = true, example = "10"),
    })

    // @PreAuthorize("@pre.hasPermission('sys:menu:list')")
    public Result<IPage<SysMenuVO>> getMenuList(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        return sysResourceService.getMenuList(params);
    }

    @Operation(summary = "查询全部页面组件名称")
    @GetMapping(value = "/getAllPages")
    public Result<List<String>> getAllPages() {
        return sysResourceService.getAllPages();
    }

    @Operation(summary = "查询菜单树")
    @GetMapping(value = "/getMenuTree")
    public Result<List<SysMenuTreeVO>> getMenuTree() {
        return sysResourceService.getMenuTree();
    }

}
