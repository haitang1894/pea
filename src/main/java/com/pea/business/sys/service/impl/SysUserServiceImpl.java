package com.pea.business.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pea.business.auth.vo.LoginResult;
import com.pea.business.sys.domain.SysUser;
import com.pea.business.sys.mapper.SysUserMapper;
import com.pea.business.sys.param.CreateUserParam;
import com.pea.business.sys.service.SysRoleService;
import com.pea.business.sys.service.SysUserService;
import com.pea.business.sys.vo.SysUserVO;
import com.pea.business.sys.vo.UserInfoVO;
import com.pea.common.api.Result;
import com.pea.common.api.ResultCode;
import com.pea.common.enums.CacheConstants;
import com.pea.common.enums.DelStatusEnums;
import com.pea.common.enums.StatusEnums;
import com.pea.common.exception.GlobalException;
import com.pea.common.exception.GlobalExceptionEnum;
import com.pea.common.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    final AuthenticationManager authenticationManager;

    final JwtTokenUtil jwtTokenUtil;

    final UserDetailsService userDetailsService;

    final SysRoleService sysRoleService;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public IPage<SysUserVO> getList(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("size")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("current")));
        LambdaQueryWrapper<SysUser> wrapper = createWrapper(params);

        IPage<SysUser> sysUserPage = page(new Page<>(pageNum, pageSize), wrapper);

        // 将查询结果中的SysUser对象转换为SysUserVO对象
        List<SysUserVO> sysUserVOList = sysUserPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());


        if (!sysUserVOList.isEmpty()) {
            sysUserVOList.forEach(userVO -> {
                Long id = userVO.getId();
                List<String> userRole = sysRoleService.getUserRole(id);
                userVO.setUserRoles(userRole);
            });
        }

        // 创建新的IPage对象，其中包含转换后的SysUserVO对象列表
        IPage<SysUserVO> sysUserVOPage = new Page<>();
        sysUserVOPage.setRecords(sysUserVOList);
        sysUserVOPage.setCurrent(sysUserPage.getCurrent());
        sysUserVOPage.setSize(sysUserPage.getSize());
        sysUserVOPage.setTotal(sysUserPage.getTotal());

        return sysUserVOPage;
    }

    public SysUserVO convertToVO(SysUser sysUser) {
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtil.copyProperties(sysUser, sysUserVO);
        return sysUserVO;
    }

    private LambdaQueryWrapper<SysUser> createWrapper(Map<String, Object> params) {
        String username = (String) params.get("username");
        String status = (String) params.get("status");

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(username), SysUser::getUserName, username);
        wrapper.eq(StrUtil.isNotEmpty(status), SysUser::getStatus, status);
        wrapper.eq( SysUser::getIsDeleted, DelStatusEnums.DISABLE.getCode());

        return wrapper;
    }

    /**
     * 更新最后一次登录时间/登录IP
     */
    private void updateLastLoginInfo(SysUser sysUser) {
        sysUser.setLastLoginIp(IpUtil.getHostIp());
        sysUser.setLastLoginTime(DateUtil.date().toLocalDateTime());
        updateById(sysUser);
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#username")
    public LoginResult login(String username, String password) {
        LoginResult res = new LoginResult();
        String token;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            token = jwtTokenUtil.generateToken(authenticate.getName());

            SysUser sysUser = SecurityUtil.getSysUser();

            // 更新最后一次登录时间
            updateLastLoginInfo(sysUser);

            res.setToken(token);
            res.setRefreshToken(token);

            return res;
        }
        catch (Exception e) {
            log.error("登录异常: {}", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public SysUser getByName(String nickName) {
        return getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getNickName, nickName));
    }

    public SysUser getByUsername(String userName) {
        return getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, userName));
    }

    private Result<String> checkoutUser(CreateUserParam userParam) {
        String password = userParam.getPassword();
        if (StrUtil.isEmpty(password)) {
            return Result.failed(ResultCode.ERROR_USER_NAME_REPEAT);
        }

        SysUser dbUserNameInfo;

        dbUserNameInfo = getByName(userParam.getNickName());
        if (dbUserNameInfo != null) {
            return Result.failed(ResultCode.ERROR_NAME_REPEAT);
        }

        dbUserNameInfo = getByUsername(userParam.getUserName());
        if (dbUserNameInfo != null) {
            return Result.failed(ResultCode.ERROR_USER_NAME_REPEAT);
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertUser(CreateUserParam createUserParam) {
        SysUser user = new SysUser();
        String password = createUserParam.getPassword();
        // 处理加密密码
        String enPassword = encoder.encode(password);

        user.setNickName(createUserParam.getNickName());
        user.setUserName(createUserParam.getUserName());
        user.setPassword(enPassword);
        user.setStatus(StatusEnums.ENABLE.getKey());

        return save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> createUser(CreateUserParam createUserParam) {
        Result<String> checkoutResult = checkoutUser(createUserParam);
        if (!Objects.equals(checkoutResult.getCode(), ResultCode.SUCCESS.getCode())) {
            return checkoutResult;
        }
        return insertUser(createUserParam) ? Result.success() : Result.failed();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<UserInfoVO> getUserInfo(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtTokenUtil.getTokenHead())) {
            throw new GlobalException(GlobalExceptionEnum.ERROR_UNAUTHORIZED.getMessage());
        }

        UserInfoVO userInfoVO = new UserInfoVO();

        try {
            String authToken = authorizationHeader.substring(jwtTokenUtil.getTokenHead().length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            if (username != null) {
                // 从数据库中获取用户信息
                SysUserDetail userDetails = (SysUserDetail) this.userDetailsService
                        .loadUserByUsername(username);

                userInfoVO.setRoles(userDetails.getRoles());
                userInfoVO.setUserName(userDetails.getUsername());
                userInfoVO.setUserId(userDetails.getSysUser().getId());

                return Result.success(userInfoVO);
            }
        } catch (Exception e) {
            log.info("获取用户信息失败: {}", e.getMessage());
            ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_UNABLE_GET_USER);
        }
        return Result.failed();
    }


}
