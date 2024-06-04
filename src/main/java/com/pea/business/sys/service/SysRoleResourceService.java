package com.pea.business.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pea.business.sys.domain.SysRoleResource;
import com.pea.common.api.Result;

import java.util.List;

public interface SysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 绑定角色资源信息
     *
     * @param roleId 角色Id
     * @param longList 资源Id
     * @return boolean
     */
    Result<Boolean> bindingRoleBasicResource(Long roleId, List<Long> longList);

    Result<List<Long>> getRoleResourceId(Long roleId);

    Boolean deleteDataByRoleId(Long roleId);

}