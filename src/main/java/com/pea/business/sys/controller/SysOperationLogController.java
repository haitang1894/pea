package com.pea.business.sys.controller;

import com.pea.business.sys.domain.SysOperationLog;
import com.pea.business.sys.service.SysOperationLogService;
import com.pea.common.api.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SysOperationLogController", description = "日志信息控制层")
@RequestMapping("/sys/log")
public class SysOperationLogController {

    final SysOperationLogService sysOperationLogService;

    @Operation(summary = "导出数据")
    @PostMapping(value = "/getExportList")
    public Result<List<SysOperationLog>> add(@RequestBody SysOperationLog sysOperationLog) {
        return Result.success(sysOperationLogService.getExportList(sysOperationLog));
    }

}
