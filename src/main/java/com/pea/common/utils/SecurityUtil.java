package com.pea.common.utils;

import com.pea.business.sys.domain.SysUser;
import com.pea.common.api.ResultCode;
import com.pea.common.exception.GlobalException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SecurityUtil
 */
public class SecurityUtil {

    public static SysUser getSysUser() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            SysUserDetail sysUserDetail = (SysUserDetail) auth.getPrincipal();
            return sysUserDetail.getSysUser();
        } catch (Exception e) {
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
    }

    public static UserDetails getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
        return userDetails;
    }

}
