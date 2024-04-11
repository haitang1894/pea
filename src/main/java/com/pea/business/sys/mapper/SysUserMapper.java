package com.pea.business.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pea.business.sys.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
