package com.pea.common.model;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pea.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Schema(description = "Meta元数据视图对象")
@Data
@EqualsAndHashCode(callSuper = true)
public class Meta extends BaseEntity {

    @Schema(description = "页面标题")
    private String title;

    @Schema(description = "国际化键")
    private String i18nKey;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "图标类型")
    private String iconType;

    @Schema(description = "顺序")
    private Integer order;

    @Schema(description = "角色列表，逗号分隔")
    private List<String> roles;

    @Schema(description = "是否缓存")
    private Boolean keepAlive;

    @Schema(description = "是否为常量路由")
    private String constant;

    @Schema(description = "本地图标路径")
    private String localIcon;

    @Schema(description = "外部链接")
    private String href;

    @Schema(description = "是否在菜单中隐藏")
    private String hideInMenu;

    @Schema(description = "激活的菜单键")
    private String activeMenu;

    @Schema(description = "是否开启多标签页模式")
    private Boolean multiTab;

    @Schema(description = "在标签页中固定索引位置")
    private Boolean fixedIndexInTab;

    // 添加无参构造函数
    protected Meta() {}

    public Meta(String metaJson) {
        JSONObject jsonObject = JSONUtil.parseObj(metaJson);

        setTitle(jsonObject.getStr("title", ""));
        setI18nKey(jsonObject.getStr("i18nKey", ""));
        setIcon(jsonObject.getStr("icon", ""));
        setIconType(jsonObject.getStr("iconType", ""));

        // 对其他字段做同样处理
        setOrder(jsonObject.getInt("order", null)); // 如果order字段可为空，则设为null，否则提供一个默认值
        if (!jsonObject.containsKey("roles")) {
            roles = Collections.emptyList(); // 或者自定义一个默认空集合
        } else {
            roles = jsonObject.getJSONArray("roles").toList(String.class); // 假设roles是一个字符串数组
        }
        setKeepAlive(jsonObject.getBool("keepAlive", false));
        setConstant(jsonObject.getStr("constant", ""));
        setLocalIcon(jsonObject.getStr("localIcon", ""));
        setHref(jsonObject.getStr("href", ""));
        setHideInMenu(jsonObject.getStr("hideInMenu", ""));
        setActiveMenu(jsonObject.getStr("activeMenu", ""));
        setMultiTab(jsonObject.getBool("multiTab", false));
        setFixedIndexInTab(jsonObject.getBool("fixedIndexInTab", false));
    }
}
