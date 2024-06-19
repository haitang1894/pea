package com.pea.common.mybatis.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pea.business.sys.domain.SysUser;
import com.pea.common.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 */
@Slf4j
@Primary
@Component
public class MybatisPlusMetaObjectHandler  implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");
        LocalDateTime localDateTime = DateUtil.date().toLocalDateTime();
        SysUser sysUser = getUser();

        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, "createId", Long.class, sysUser.getId());
        this.strictInsertFill(metaObject, "createBy", String.class, sysUser.getUserName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        LocalDateTime localDateTime = DateUtil.date().toLocalDateTime();
        SysUser sysUser = getUser();

        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, "updateId", Long.class, sysUser.getId());
        this.strictInsertFill(metaObject, "updateBy", String.class, sysUser.getUserName());
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal,
                                            MetaObject metaObject, boolean isCover) {
        // 1. 没有 set 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取 spring security 当前的用户
     * @return 当前用户
     */
    private SysUser getUser() {
        return SecurityUtil.getSysUser();
    }

}
