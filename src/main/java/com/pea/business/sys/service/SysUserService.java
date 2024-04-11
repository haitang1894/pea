package com.pea.business.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pea.business.auth.vo.LoginResult;
import com.pea.business.sys.domain.SysUser;
import com.pea.business.sys.param.CreateUserParam;
import com.pea.business.sys.vo.SysUserVO;
import com.pea.business.sys.vo.UserInfoVO;
import com.pea.common.api.Result;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    /**
     * 分页获取数据
     *
     * @param params 查询参数
     * @return IPage<SysUserVO>
     */
    IPage<SysUserVO> getList(Map<String, Object> params);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    LoginResult login(String username, String password);

    /**
     * 登录
     *
     * @param createUserParam 创建用户信息
     * @return 创建结果
     */
    Result<String> createUser(CreateUserParam createUserParam);

    /**
     * 获取用户信息
     *
     * @return UserInfoVO
     */
    Result<UserInfoVO> getUserInfo(String authorizationHeader);
}
