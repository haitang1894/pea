package com.pea.business.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pea.business.sys.domain.SysUserRole;
import com.pea.common.api.Result;

import java.util.List;

public interface SysUserRoleService  extends IService<SysUserRole> {

    /**
     * 绑定用户角色
     *
     * @param roleCodes 角色code集合
     * @param userId 用户Id
     * @return boolean
     */
    Result<Boolean> bindingUserRoles(List<String> roleCodes, Long userId);

}