package com.pea.business.sys.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ResourceParam {

    @Schema(description = "资源ID")
    private Long id;

    @Schema(description = "父节点ID")
    private Integer parentId;

    @Schema(description = "菜单类型：1-菜单路由，2-资源（按钮等）")
    private String menuType;

    @Schema(description = "资源名称")
    private String menuName;

    @Schema(description = "路由名称")
    private String routeName;

    @Schema(description = "路由路径")
    private String routePath;

    @Schema(description = "路径参数")
    private String pathParam;

    @Schema(description = "组件路径或名称")
    private String component;

    @Schema(description = "布局类型")
    private String layout;

    @Schema(description = "页面关联标识")
    private String page;

    @Schema(description = "国际化键")
    private String i18nKey;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "本地图标路径")
    private String localIcon;

    @Schema(description = "图标类型：1-默认类型")
    private String iconType;

    @Schema(description = "状态：1-可用，2-禁用")
    private String status;

    @Schema(description = "是否缓存")
    private Boolean keepAlive;

    @Schema(description = "是否为常量资源")
    private Boolean constant;

    @Schema(description = "排序号")
    private Integer order;

    @Schema(description = "外部链接")
    private String href;

    @Schema(description = "是否在菜单中隐藏")
    private Boolean hideInMenu;

    @Schema(description = "激活时对应的菜单路径")
    private String activeMenu;

    @Schema(description = "是否支持多标签页")
    private Boolean multiTab;

    @Schema(description = "在多标签页中的固定索引")
    private Integer fixedIndexInTab;

    @Schema(description = "角色列表，逗号分隔")
    private List<String> roles;

    @Schema(description = "查询参数集合")
    private List<String> query;

    @Schema(description = "按钮权限集合")
    private List<String> buttons;
}