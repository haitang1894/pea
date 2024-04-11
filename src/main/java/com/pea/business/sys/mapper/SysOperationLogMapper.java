package com.pea.business.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pea.business.sys.domain.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    int clean();

}
