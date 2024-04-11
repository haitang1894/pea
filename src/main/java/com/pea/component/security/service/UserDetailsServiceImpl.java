package com.pea.component.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pea.business.sys.domain.SysUser;
import com.pea.business.sys.mapper.SysResourceMapper;
import com.pea.business.sys.mapper.SysRoleMapper;
import com.pea.business.sys.mapper.SysUserMapper;
import com.pea.common.enums.CacheConstants;
import com.pea.common.enums.StatusEnums;
import com.pea.common.exception.GlobalExceptionEnum;
import com.pea.common.utils.ExceptionUtil;
import com.pea.common.utils.SysUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CacheManager cacheManager;

    private final SysUserMapper sysUserMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysResourceMapper sysResourceMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (SysUserDetail) Objects.requireNonNull(cache.get(username)).get();
        }

        // 获取登录用户信息
        SysUser user = sysUserMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, username));

        // 用户不存在
        if (ObjectUtil.isEmpty(user)) {
            ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        // 用户停用
        if (StatusEnums.DISABLE.getKey().equals(user.getStatus())) {
            ExceptionUtil.throwEx(GlobalExceptionEnum.USER_DISABLED);
        }

        // 获取用户菜单权限标识
        List<String> permissions = sysResourceMapper.getUserPermissions(user.getId());

        // 获取用户角色
        List<String> userRole = sysRoleMapper.getUserRole(user.getId());

        SysUserDetail userDetails = new SysUserDetail(user, permissions, userRole);

        if (cache != null) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }
}
