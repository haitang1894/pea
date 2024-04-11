package com.pea.business.auth.controller;

import com.pea.business.auth.param.SysUserLoginParam;
import com.pea.business.auth.vo.LoginResult;
import com.pea.business.sys.service.SysUserService;
import com.pea.business.sys.vo.UserInfoVO;
import com.pea.common.annotation.SysLogInterface;
import com.pea.common.api.Result;
import com.pea.common.enums.BusinessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SysUserLoginController")
@RequestMapping("/auth")
public class SysUserLoginController {

    private final SysUserService sysUserService;

    @Operation(summary = "登录")
    @PostMapping(value = "/login")
    @SysLogInterface(title = "登录", businessType = BusinessType.GRANT)
    public Result<LoginResult> login(@RequestBody SysUserLoginParam sysUserLoginParam) {
        // 获取系统验证码开关
        // boolean sw = Boolean.parseBoolean(ParamResolver.getStr(ConfigEnums.SYS_CAPTCHA_IMG.name(), "true"));
        // if (sw) {
        // 验证码校验
        // boolean captcha = sysCaptchaService.validate(sysUserLoginParam.getUuid(),
        //         sysUserLoginParam.getCode());
        // if (!captcha) {
        //     return Result.failed("验证码不正确");
        // }
        // }
        return Result.success(sysUserService.login(sysUserLoginParam.getUserName(),
                sysUserLoginParam.getPassword()));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result<UserInfoVO> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        return sysUserService.getUserInfo(authorizationHeader);
    }

}
