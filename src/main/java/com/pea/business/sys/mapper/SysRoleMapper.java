package com.pea.business.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pea.business.sys.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper  extends BaseMapper<SysRole> {

    List<String> getUserRole(Long id);

}
