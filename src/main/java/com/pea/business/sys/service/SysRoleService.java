package com.pea.business.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pea.business.sys.domain.SysRole;
import com.pea.business.sys.vo.SysRoleVO;
import com.pea.common.api.Result;

import java.util.List;
import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页获取数据
     *
     * @param params 查询参数
     * @return IPage<SysRole>
     */
    IPage<SysRole> getPage(Map<String, Object> params);

    Result<List<SysRoleVO>> getAllRoles(String authorizationHeader);

    List<String> getUserRole(Long id);

    List<SysRole> queryRoleListByRoleCode(List<String> roleCode);

    Result<Boolean> add(SysRole sysRole);

    Result<List<Long>> getRoleResourceId(Long roleId);

    Result<Boolean> updateRoleResourceInfo(Long roleId, List<Long> resourceId);
}
