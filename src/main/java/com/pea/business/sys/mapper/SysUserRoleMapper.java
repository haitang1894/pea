package com.pea.business.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pea.business.sys.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}