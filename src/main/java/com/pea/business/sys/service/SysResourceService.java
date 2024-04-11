package com.pea.business.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pea.business.sys.domain.SysResource;
import com.pea.business.sys.vo.SysMenuVO;
import com.pea.business.sys.vo.SysRoutesVO;
import com.pea.common.api.Result;

import java.util.List;
import java.util.Map;

public interface SysResourceService extends IService<SysResource> {

    Result<Map<String, Object>> getUserRoutes(String authorizationHeader);

    Result<IPage<SysMenuVO>> getMenuList(Map<String, Object> params);

    Result<List<SysRoutesVO>> getConstantRoutes();

    List<String> getUserPermissions(Long id);

    Result<List<String>> getAllPages();
}
