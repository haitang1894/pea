package com.pea.common.utils;

import cn.hutool.json.JSONUtil;
import com.pea.business.sys.domain.SysResource;
import com.pea.business.sys.vo.SysMenuVO;
import com.pea.business.sys.vo.SysRoutesVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
