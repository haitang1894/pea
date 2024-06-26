package com.pea.business.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pea.business.sys.domain.SysResource;
import com.pea.business.sys.mapper.SysResourceMapper;
import com.pea.business.sys.service.SysResourceService;
import com.pea.business.sys.vo.SysMenuTreeVO;
import com.pea.business.sys.vo.SysMenuVO;
import com.pea.business.sys.vo.SysRoutesVO;
import com.pea.common.api.Result;
import com.pea.common.api.ResultCode;
import com.pea.common.enums.DelStatusEnums;
import com.pea.common.enums.MenuTypeEnums;
import com.pea.common.enums.StatusEnums;
import com.pea.common.exception.GlobalException;
import com.pea.common.exception.GlobalExceptionEnum;
import com.pea.common.model.Meta;
import com.pea.common.utils.ExceptionUtil;
import com.pea.common.utils.JwtTokenUtil;
import com.pea.common.utils.RouteUtil;
import com.pea.common.utils.SysUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
        implements SysResourceService {

    private final JwtTokenUtil jwtTokenUtil;
    private static final RouteUtil routeUtil = new RouteUtil();

    final UserDetailsService userDetailsService;

    @Override
    public Result<Map<String, Object>> getUserRoutes(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtTokenUtil.getTokenHead())) {
            throw new GlobalException(GlobalExceptionEnum.ERROR_UNAUTHORIZED.getMessage());
        }

        Map<String, Object> map = new HashMap<>();

        try {
            String authToken = authorizationHeader.substring(jwtTokenUtil.getTokenHead().length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            if (username != null) {
                // 从数据库中获取用户信息
                SysUserDetail userDetails = (SysUserDetail) this.userDetailsService
                        .loadUserByUsername(username);

                String id = userDetails.getSysUser()
                        .getId().toString();

                List<SysResource> userRoutes = baseMapper.getUserRoutes(id);

                List<SysRoutesVO> routesVOList = routeUtil.processRoute(userRoutes);

                map.put("home", "home");
                map.put("routes", routesVOList);
                return Result.success(map);
            }
        } catch (Exception e) {
            log.info("获取资源信息异常: {}", e.getMessage());
            ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_GAIN_RESOURCE);
        }

        return Result.success();
    }

    @Override
    public Result<IPage<SysMenuVO>> getMenuList(Map<String, Object> params) {
        int pageNum = 1;
        int pageSize = 10;

        if (ObjUtil.isNotEmpty(params)) {
            pageSize = Integer.parseInt(String.valueOf(params.get("size")));
            pageNum = Integer.parseInt(String.valueOf(params.get("current")));
        }

        List<SysResource> list = getBaseQueryWrapper();

        List<SysMenuVO> menuVOS = convertSysResourceToMenuVO(list);

        List<SysMenuVO> processedMenuVOS = routeUtil.processMenu(menuVOS);

        List<SysMenuVO> pagedMenuVOS = paginate(processedMenuVOS, pageNum, pageSize);

        IPage<SysMenuVO> sysMenuVOIPage = new Page<>();
        sysMenuVOIPage.setRecords(pagedMenuVOS);
        sysMenuVOIPage.setCurrent(pageNum);
        sysMenuVOIPage.setSize(pageSize);
        sysMenuVOIPage.setTotal(processedMenuVOS.size());

        return Result.success(sysMenuVOIPage);
    }

    // Method to create base LambdaQueryWrapper
    private List<SysResource> getBaseQueryWrapper() {
        LambdaQueryWrapper<SysResource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysResource::getIsDeleted, DelStatusEnums.DISABLE.getCode());
        lambdaQueryWrapper.notIn(SysResource::getMenuType, MenuTypeEnums.BASIC_MENU.getCode(), MenuTypeEnums.BUTTON.getCode());
        return list(lambdaQueryWrapper);
    }

    // Method to convert SysResource to SysMenuVO
    private List<SysMenuVO> convertSysResourceToMenuVO(List<SysResource> resources) {
        List<SysMenuVO> menuVOList = new ArrayList<>();
        resources.forEach(item -> {
            String meta = item.getMeta();
            SysMenuVO sysMenuVO = convertMetaJsonToSysMenuVO(meta);
            sysMenuVO.setId(item.getId());
            sysMenuVO.setParentId(item.getParentId());
            sysMenuVO.setUiPath(item.getUiPath());
            sysMenuVO.setMenuType(item.getMenuType());
            sysMenuVO.setStatus(item.getStatus());
            sysMenuVO.setMenuName(item.getMenuName());
            sysMenuVO.setRouteName(item.getRouteName());
            sysMenuVO.setRoutePath(item.getRoutePath());
            sysMenuVO.setComponent(item.getComponent());
            sysMenuVO.setWeight(item.getWeight());
            sysMenuVO.setCreateId(item.getCreateId());
            sysMenuVO.setCreateBy(item.getCreateBy());
            sysMenuVO.setCreateTime(item.getCreateTime());

            sysMenuVO.setUpdateId(item.getUpdateId());
            sysMenuVO.setUpdateBy(item.getUpdateBy());
            sysMenuVO.setUpdateTime(item.getUpdateTime());

            sysMenuVO.setIsDeleted(item.getIsDeleted());
            sysMenuVO.setDeleteTime(item.getDeleteTime());
            menuVOList.add(sysMenuVO);
        });
        return menuVOList;
    }

    // Method to paginate a list
    private <T> List<T> paginate(List<T> list, int pageNum, int pageSize) {
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        return list.subList(start, end);
    }

    public static SysMenuVO convertMetaJsonToSysMenuVO(String metaJson) {
        // 创建Meta对象并填充属性
        Meta meta = new Meta(metaJson);
        // 创建并初始化SysMenuVO对象
        SysMenuVO sysMenuVO = new SysMenuVO();
        // 将Meta对象的属性赋值给SysMenuVO对象
        BeanUtil.copyProperties(meta, sysMenuVO, false);

        return sysMenuVO;
    }

    @Override
    public Result<List<SysRoutesVO>> getConstantRoutes() {

        LambdaQueryWrapper<SysResource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysResource::getIsDeleted, DelStatusEnums.DISABLE.getCode());
        lambdaQueryWrapper.eq(SysResource::getMenuType, MenuTypeEnums.BASIC_MENU.getCode());

        List<SysResource> list = list(lambdaQueryWrapper);

        List<SysRoutesVO> routesVOList = routeUtil.processRoute(list);
        return Result.success(routesVOList);
    }

    @Override
    public List<String> getUserPermissions(Long id) {
        return baseMapper.getUserPermissions(id);
    }

    @Override
    public Result<List<String>> getAllPages() {
        List<SysResource> list = getBaseQueryWrapper();

        List<String> routeNames = list.stream()
                .map(SysResource::getRouteName)
                .collect(Collectors.toList());

        return Result.success(routeNames);
    }

    @Override
    public Result<List<SysMenuTreeVO>> getMenuTree() {
        try {
            List<SysResource> userRoutes = getBaseQueryWrapper();

            List<SysMenuTreeVO> menuTree = routeUtil.getMenuTree(userRoutes);

            return Result.success(menuTree);
        } catch (Exception e) {
            log.info("获取菜单权限信息异常: {}", e.getMessage());
            throw new GlobalException(ResultCode.ERROR_GET_MENU_PERMISSIONS);
        }
    }

    @Override
    public Result<Integer> add(SysResource sysResource) {
        if (sysResource.getId() != 0) {
            // update
            if (StrUtil.isNotEmpty(sysResource.getRoutePath())) {

                LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();

                sysResourceLambdaQueryWrapper.eq(SysResource::getIsDeleted, DelStatusEnums.DISABLE.getCode());
                sysResourceLambdaQueryWrapper.eq(SysResource::getRoutePath, sysResource.getRoutePath());

                List<SysResource> sysResources = list(sysResourceLambdaQueryWrapper);

                if (sysResources.isEmpty()) {
                    return Result.failed(ResultCode.ERROR_RESOURCE_EXISTENCE);
                }

                log.info("修改资源入参: {}", JSONUtil.parse(sysResource));

                LambdaUpdateWrapper<SysResource> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(SysResource::getId, sysResource.getId());

                update(sysResource, lambdaUpdateWrapper);

                return Result.success();
            }
        }

        // add
        // 随机8位字符串
        String randomString = RandomUtil.randomString(8);

        String uiPath;

        if (ObjUtil.isNotEmpty(sysResource.getParentId()) && sysResource.getParentId() != 0) {

            LambdaQueryWrapper<SysResource> sysResourceLambdaQueryWrapper = new LambdaQueryWrapper<>();

            sysResourceLambdaQueryWrapper.eq(SysResource::getIsDeleted, DelStatusEnums.DISABLE.getCode());
            sysResourceLambdaQueryWrapper.eq(SysResource::getId, sysResource.getParentId());

            List<SysResource> sysResources = list(sysResourceLambdaQueryWrapper);

            if (sysResources.isEmpty()) {
                return Result.failed(ResultCode.ERROR_PARENT_RESOURCE_DOES_NOT_EXIST);
            }

            sysResource.setUiPath(sysResources.getFirst().getUiPath() + randomString + sysResource.getRoutePath());

        }

        uiPath = randomString + sysResource.getRoutePath();

        sysResource.setUiPath(uiPath);
        sysResource.setStatus(StatusEnums.ENABLE.getCode());
        sysResource.setIsDeleted(DelStatusEnums.DISABLE.getCode());

        log.info("新增资源入参: {}", JSONUtil.parse(sysResource));

        save(sysResource);

        return Result.success();
    }

}
