package com.pea.common.utils;

import cn.hutool.core.util.StrUtil;
import com.pea.business.sys.domain.SysUser;
import com.pea.common.enums.StatusEnums;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity 需要的用户详情
 */
@Getter
public class SysUserDetail implements UserDetails {

    private final SysUser sysUser;

    private final List<String> permissions;

    private final List<String> roles;

    public SysUserDetail(SysUser sysUser, List<String> permissions, List<String> roles) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的权限
        return permissions.stream().filter(StrUtil::isNotEmpty)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @Override
    public boolean isEnabled() {
        return StatusEnums.ENABLE.getKey().equals(sysUser.getStatus());
    }

}
