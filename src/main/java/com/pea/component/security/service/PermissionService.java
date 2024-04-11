package com.pea.component.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.pea.common.utils.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 权限处理器
 */
@Service("pre")
public class PermissionService {

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    public Boolean hasPermission(String... permissions) {
        if (ObjectUtil.isEmpty(permissions)) {
            return false;
        }
        // 获取当前用户的所有权限
        List<String> perms = SecurityUtil.getUserDetails().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        // 判断当前用户的所有权限是否包含接口上定义的权限
        return perms.contains(ALL_PERMISSION)
                || Arrays.stream(permissions).anyMatch(perms::contains);
    }

}
