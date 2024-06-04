package com.pea.business.sys.controller;

import com.pea.business.sys.domain.SysResource;
import com.pea.business.sys.param.ResourceParam;
import com.pea.business.sys.service.SysResourceService;
import com.pea.common.api.Result;
import com.pea.common.utils.RouteUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SysResourceController", description = "资源信息控制层")
@RequestMapping("/sys/resource")
public class SysResourceController {

    private static final RouteUtil routeUtil = new RouteUtil();

    private final SysResourceService sysResourceService;

    @Operation(summary = "新增资源信息")
    @PostMapping(value = "/add")
    public Result<Integer> add(@RequestBody ResourceParam resourceParam) {
        SysResource sysResource = routeUtil.paramConvertSysResource(resourceParam);
        return sysResourceService.add(sysResource);
    }

}
