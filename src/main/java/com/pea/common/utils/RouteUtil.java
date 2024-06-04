package com.pea.common.utils;

import cn.hutool.json.JSONUtil;
import com.pea.business.sys.domain.SysResource;
import com.pea.business.sys.param.ResourceParam;
import com.pea.business.sys.vo.SysMenuTreeVO;
import com.pea.business.sys.vo.SysMenuVO;
import com.pea.business.sys.vo.SysRoutesVO;
import com.pea.common.model.Meta;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * JwtToken生成的工具类
 */
@Slf4j
public class RouteUtil {

    public List<SysRoutesVO> processRoute(List<SysResource> resourceList) {
        Map<Long, SysRoutesVO> map = new HashMap<>();

        // 先将所有资源按照父节点ID进行分组
        for (SysResource sysResource : resourceList) {
            SysRoutesVO sysRoutesVO = new SysRoutesVO();
            sysRoutesVO.setName(sysResource.getRouteName());
            sysRoutesVO.setPath(sysResource.getRoutePath());
            sysRoutesVO.setComponent(sysResource.getComponent());
            sysRoutesVO.setMeta(JSONUtil.parse(sysResource.getMeta()));
            sysRoutesVO.setChildren(new ArrayList<>());

            map.put(sysResource.getId(), sysRoutesVO);

            // 如果是根节点则直接加入结果列表
            if (sysResource.getParentId() == 0) {
                map.put(sysResource.getId(), sysRoutesVO);
            } else {
                // 如果不是根节点，则加入父节点的 children 列表
                SysRoutesVO parent = map.get(Long.parseLong(sysResource.getParentId().toString()));
                if (parent != null) {
                    parent.getChildren().add(sysRoutesVO);
                }
            }
        }

        // 获取所有根节点并返回
        List<SysRoutesVO> result = new ArrayList<>();
        for (SysResource sysResource : resourceList) {
            if (sysResource.getParentId() == 0) {
                result.add(map.get(sysResource.getId()));
            }
        }
        return result;
    }

    public List<SysMenuVO> processMenu(List<SysMenuVO> sysMenuVOList) {
        Map<Long, SysMenuVO> map = new HashMap<>();
        // 先将所有资源按照父节点ID进行分组
        for (SysMenuVO sysMenuVO : sysMenuVOList) {
            sysMenuVO.setChildren(new ArrayList<>()); // 初始化children属性

            map.put(sysMenuVO.getId(), sysMenuVO);

            // 如果是根节点则直接加入结果列表
            if (sysMenuVO.getParentId() == 0) {
                map.put(sysMenuVO.getId(), sysMenuVO);
            } else {
                // 如果不是根节点，则加入父节点的 children 列表
                SysMenuVO parent = map.get(Long.parseLong(sysMenuVO.getParentId().toString()));
                if (parent != null) {
                    parent.getChildren().add(sysMenuVO);
                }
            }
        }

        // 获取所有根节点并返回
        List<SysMenuVO> result = new ArrayList<>();
        for (SysMenuVO sysMenuVO : sysMenuVOList) {
            if (sysMenuVO.getParentId() == 0) {
                result.add(map.get(sysMenuVO.getId()));
            }
        }
        return result;
    }

    public List<SysMenuTreeVO> getMenuTree(List<SysResource> resourceList) {
        Map<Long, SysMenuTreeVO> map = new HashMap<>();

        // 先将所有资源按照父节点ID进行分组
        for (SysResource sysResource : resourceList) {

            SysMenuTreeVO sysMenuTreeVO = new SysMenuTreeVO();
            sysMenuTreeVO.setId(sysResource.getId());
            sysMenuTreeVO.setPId(sysResource.getParentId());
            sysMenuTreeVO.setLabel(sysResource.getMenuName());
            sysMenuTreeVO.setChildren(new ArrayList<>());


            map.put(sysResource.getId(), sysMenuTreeVO);

            // 如果是根节点则直接加入结果列表
            if (sysResource.getParentId() == 0) {
                map.put(sysResource.getId(), sysMenuTreeVO);
            } else {
                // 如果不是根节点，则加入父节点的 children 列表
                SysMenuTreeVO parent = map.get(Long.parseLong(sysResource.getParentId().toString()));
                if (parent != null) {
                    parent.getChildren().add(sysMenuTreeVO);
                }
            }
        }

        // 获取所有根节点并返回
        List<SysMenuTreeVO> result = new ArrayList<>();
        for (SysResource sysResource : resourceList) {
            if (sysResource.getParentId() == 0) {
                result.add(map.get(sysResource.getId()));
            }
        }

        sortChildren(result);
        return result;

    }

    /**
     * 递归排序所有层级的children
     * @param menus 菜单列表
     */
    private static void sortChildren(List<SysMenuTreeVO> menus) {

        menus.sort(Comparator.comparingLong(SysMenuTreeVO::getId));

        for (SysMenuTreeVO menu : menus) {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                // 先对当前菜单的children排序
                menu.getChildren().sort(Comparator.comparingLong(SysMenuTreeVO::getId));
                // 递归排序子菜单的children
                sortChildren(menu.getChildren());
            }
        }
    }

    public SysResource paramConvertSysResource(ResourceParam resourceParam) {

        log.info("资源添加接口入参: {}", JSONUtil.parse(resourceParam));

        SysResource sysResource = new SysResource();
        sysResource.setId(resourceParam.getId());
        sysResource.setParentId(resourceParam.getParentId());
        sysResource.setMenuType(resourceParam.getMenuType());
        sysResource.setStatus(resourceParam.getStatus());
        sysResource.setMenuName(resourceParam.getMenuName());
        sysResource.setRouteName(resourceParam.getRouteName());
        sysResource.setRoutePath(resourceParam.getRoutePath());
        sysResource.setComponent(resourceParam.getComponent());

        Meta meta = getMeta(resourceParam);

        String str = JSONUtil.toJsonStr(meta);

        sysResource.setMeta(str);

        log.info("资源添加接口转换后的参数: {}", JSONUtil.parse(sysResource));

        return sysResource;
    }

    private static Meta getMeta(ResourceParam resourceParam) {
        Meta meta = new Meta();
        meta.setI18nKey(resourceParam.getI18nKey());
        meta.setIconType(resourceParam.getIconType());
        meta.setIcon(resourceParam.getIcon());
        meta.setOrder(resourceParam.getOrder());
        meta.setRoles(resourceParam.getRoles());
        meta.setKeepAlive(resourceParam.getKeepAlive());
        meta.setConstant(resourceParam.getConstant());
        meta.setLocalIcon(resourceParam.getLocalIcon());
        meta.setHref(resourceParam.getHref());
        meta.setHideInMenu(resourceParam.getHideInMenu());
        meta.setActiveMenu(resourceParam.getActiveMenu());
        meta.setMultiTab(resourceParam.getMultiTab());
        meta.setFixedIndexInTab(resourceParam.getFixedIndexInTab());
        meta.setQuery(resourceParam.getQuery());
        return meta;
    }


}
