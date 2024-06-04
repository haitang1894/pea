package com.pea.business.sys.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pea.business.sys.domain.SysRoleResource;
import com.pea.business.sys.mapper.SysRoleResourceMapper;
import com.pea.business.sys.service.SysRoleResourceService;
import com.pea.common.api.Result;
import com.pea.common.enums.DelStatusEnums;
import com.pea.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource>
        implements SysRoleResourceService {

    /**
     * 绑定角色基础资源信息
     *
     * @param roleId 角色Id
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public Result<Boolean> bindingRoleBasicResource(Long roleId, List<Long> longList) {

        log.info("绑定角色基础资源信息 -> 角色Id: {} , 资源信息: {}", roleId, JSONUtil.parse(longList));

        List<SysRoleResource> sysRoleResourceList = new ArrayList<>();

        longList.forEach(item -> {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(roleId);
            sysRoleResource.setResourceId(item);
            sysRoleResource.setIsDeleted(DelStatusEnums.DISABLE.getCode());
            sysRoleResourceList.add(sysRoleResource);
        });

        return Result.success(saveBatch(sysRoleResourceList));
    }

    @Override
    public Result<List<Long>> getRoleResourceId(Long roleId) {


        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleResource::getRoleId, roleId);
        queryWrapper.eq(SysRoleResource::getIsDeleted, DelStatusEnums.DISABLE.getCode());

        List<SysRoleResource> roleResources = list(queryWrapper);

        List<Long> longList = new ArrayList<>();

        for (SysRoleResource roleResource : roleResources) {
            longList.add(roleResource.getResourceId());
        }

        return Result.success(longList);
    }

    @Override
    public Boolean deleteDataByRoleId(Long roleId) {

        log.info("删除角色下所有资源信息 -> 角色ID: {}", roleId);

        LambdaQueryWrapper<SysRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleResource::getRoleId, roleId);

        return remove(queryWrapper);
    }
}