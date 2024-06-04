
-- ----------------------------
-- 导出平台: mayfly-go
-- 导出时间: 2024-04-10 19:22:22 
-- 导出数据库: pea 
-- ----------------------------

USE `pea`;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 表结构: t_sys_config 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_type` varchar(50) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '系统内置: Y N ',
  `param_key` varchar(50) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT 'key',
  `param_value` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT 'value',
  `param_name` varchar(2000) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT 'name',
  `status` char(1) COLLATE utf8mb4_unicode_520_ci DEFAULT '0' COMMENT '状态   0：禁用   1：启用',
  `remark` varchar(500) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `valid` int DEFAULT '1' COMMENT '有效状态：0->无效；1->有效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `param_key_idx` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci COMMENT='系统配置信息表';

-- ----------------------------
-- 表记录: t_sys_config 
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- 表结构: t_sys_operation_log 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_operation_log`;
CREATE TABLE `t_sys_operation_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `business_type` tinyint NOT NULL COMMENT '类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据）',
  `method` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求方式',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '描述',
  `req_ip` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '请求IP',
  `req_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '请求信息',
  `resp` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '响应信息',
  `error_msg` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT '错误消息',
  `create_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_by` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '创建者名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint DEFAULT NULL COMMENT '修改者ID',
  `update_by` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '修改者名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint DEFAULT NULL COMMENT '是否已删除：0->未删除；1->已删除',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `oper_name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人',
  `oper_location` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作地点',
  `status` tinyint DEFAULT NULL COMMENT '状态 1-可用，2-禁用',
  PRIMARY KEY (`id`),
  KEY `t_sys_operation_log_id_idx` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统操作日志';

-- ----------------------------
-- 表结构: t_sys_resource 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE `t_sys_resource` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int NOT NULL COMMENT '父节点id',
  `ui_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '唯一标识路径',
  `menu_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '1：菜单路由；2：资源（按钮）3: 基础资源',
  `status` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '状态；1:可用，2:禁用',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '路由名称',
  `route_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单路由为path，其他为唯一标识',
  `component` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `meta` varchar(455) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '元数据',
  `weight` int DEFAULT NULL COMMENT '权重顺序',
  `create_id` bigint NOT NULL COMMENT '创建者ID',
  `create_by` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '创建者名称',
  `update_id` bigint NOT NULL COMMENT '修改者ID',
  `update_by` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '修改者名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否已删除：0->未删除；1->已删除',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `t_sys_resource_ui_path_IDX` (`ui_path`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='资源表';

-- ----------------------------
-- 表记录: t_sys_resource 
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_resource` VALUES (1, 0, 'home/', '2', '1', '首页', 'home', '/home', 'layout.base$view.home', '{"iconType": "1","icon": "mdi:monitor-dashboard", "order": 0, "title": "home", "i18nKey": "route.home"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (2, 0, '/exception', '1', '1', '异常页面', 'exception', '/exception', 'layout.base', '{"iconType": "1","icon": "ant-design:exception-outlined", "order": 7, "title": "exception", "i18nKey": "route.exception"}', 7, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (3, 2, '/exception/403', '2', '1', '403', 'exception_403', '/exception/403', 'view.403', '{"iconType": "1","icon": "ic:baseline-block", "title": "exception_403", "i18nKey": "route.exception_403"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (4, 2, '/exception/404', '2', '1', '404', 'exception_404', '/exception/404', 'view.404', '{"iconType": "1","icon": "ic:baseline-web-asset-off", "title": "exception_404", "i18nKey": "route.exception_404"}', 2, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (5, 2, '/exception/500', '2', '1', '500', 'exception_500', '/exception/500', 'view.500', '{"iconType": "1","icon": "ic:baseline-wifi-off", "title": "exception_500", "i18nKey": "route.exception_500"}', 3, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (6, 0, '/about', '2', '1', '关于', 'about', '/about', 'layout.base$view.about', '{"iconType": "1","icon": "fluent:book-information-24-regular", "order": 10, "title": "about", "i18nKey": "route.about"}', 10, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-04-03 05:38:24', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (7, 0, '/function', '1', '1', '系统功能', 'function', '/function', 'layout.base', '{"iconType": "1","icon": "icon-park-outline:all-application", "order": 6, "title": "function", "i18nKey": "route.function"}', 6, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (8, 7, '/function/multi-tab', '2', '1', '多标签页', 'function_multi-tab', '/function/multi-tab', 'view.function_multi-tab', '{"iconType": "1","icon": "ic:round-tab", "title": "function_multi-tab", "i18nKey": "route.function_multi-tab", "multiTab": true, "hideInMenu": true, "activeMenu": "function_tab"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (9, 0, '/multi-menu', '1', '1', '多级菜单', 'multi-menu', '/multi-menu', 'layout.base', '{"iconType": "1","icon": "mdi:menu", "order": 4, "title": "multi-menu", "i18nKey": "route.multi-menu"}', 4, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (10, 9, '/multi-menu/first', '1', '1', '菜单一', 'multi-menu_first', '/multi-menu/first', NULL, '{"iconType": "1","icon": "mdi:menu", "order": 1, "title": "multi-menu_first", "i18nKey": "route.multi-menu_first"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (11, 10, '/multi-menu/first/child', '2', '1', '菜单一子菜单', 'multi-menu_first_child', '/multi-menu/first/child', 'view.multi-menu_first_child', '{"iconType": "1","icon": "mdi:menu", "order": 1, "title": "multi-menu_first_child", "i18nKey": "route.multi-menu_first_child"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (12, 9, '/multi-menu/second', '1', '1', '菜单二', 'multi-menu_second', '/multi-menu/second', NULL, '{"iconType": "1","icon": "mdi:menu", "order": 13, "title": "multi-menu_second", "i18nKey": "route.multi-menu_second"}', 13, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (14, 12, '/multi-menu/second/child', '1', '1', '菜单二子菜单', 'multi-menu_second_child', '/multi-menu/second/child', NULL, '{"iconType": "1","icon": "mdi:menu", "order": 1, "title": "multi-menu_second_child", "i18nKey": "route.multi-menu_second_child"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (15, 14, '/multi-menu/second/child/home', '2', '1', '菜单二子菜单首页', 'multi-menu_second_child_home', '/multi-menu/second/child/home', 'view.multi-menu_second_child_home', '{"iconType": "1","icon": "mdi:menu", "order": 1, "title": "multi-menu_second_child_home", "i18nKey": "route.multi-menu_second_child_home"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (16, 7, '/function/tab', '2', '1', '标签页', 'function_tab', '/function/tab', 'view.function_tab', '{"iconType": "1","icon": "ic:round-tab", "title": "function_tab", "i18nKey": "route.function_tab"}', 2, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (17, 0, '/user-center', '2', '1', '个人中心', 'user-center', '/user-center', 'layout.base$view.user-center', '{"iconType": "1","icon": "mdi:monitor-dashboard", "title": "个人中心", "i18nKey": "route.user-center", "hideInMenu": true}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-04-03 05:38:50', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (18, 0, '/manage', '1', '1', '系统管理', 'manage', '/manage', 'layout.base', '{"iconType": "1","icon": "carbon:cloud-service-management", "order": 9, "title": "系统管理", "i18nKey": "route.manage",}', 9, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (19, 18, '/manage/user', '2', '1', '用户管理', 'manage_user', '/manage/user', 'view.manage_user', '{"iconType": "1","icon": "ic:round-manage-accounts", "order": 1, "title": "用户管理", "i18nKey": "route.manage_user"}', 1, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (20, 18, '/manage/role', '2', '1', '角色管理', 'manage_role', '/manage/role', 'view.manage_role', '{"iconType": "1","icon": "carbon:user-role", "order": 2, "title": "角色管理", "i18nKey": "route.manage_role"}', 2, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (21, 18, '/manage/menu', '2', '1', '菜单管理', 'manage_menu', '/manage/menu', 'view.manage_menu', '{"iconType": "1","icon": "material-symbols:route", "order": 3, "title": "菜单管理", "i18nKey": "route.manage_menu"}', 3, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 0, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (22, 18, '/manage/user-detail/:id', '3', '1', '用户详情', 'manage_user-detail', '/manage/user-detail/:id', 'view.manage_user-detail', '{"title": "manage_user-detail", "i18nKey": "route.manage_user-detail", "hideInMenu": true}', 4, 1, 'admin', 1, 'admin', '2024-03-09 08:49:27', '2024-03-09 08:49:30', 1, '2024-03-09 08:49:34');
INSERT INTO `t_sys_resource` VALUES (23, 0, '403', '4', '1', '403', '403', '/403', 'layout.blank$view.403', '{"constant": true, "hideInMenu": true, "title": "403", "i18nKey": "route.403"}', 1, 1, 'admin', 1, 'admin', '2024-03-26 08:49:27', '2024-03-26 08:49:30', 0, NULL);
INSERT INTO `t_sys_resource` VALUES (24, 0, '404', '4', '1', '404', '404', '/404', 'layout.blank$view.404', '{"constant": true, "hideInMenu": true, "title": "404", "i18nKey": "route.404"}', 1, 1, 'admin', 1, 'admin', '2024-03-26 08:49:27', '2024-03-26 08:49:30', 0, NULL);
INSERT INTO `t_sys_resource` VALUES (25, 0, '500', '4', '1', '500', '500', '/500', 'layout.blank$view.500', '{"constant": true, "hideInMenu": true, "title": "500", "i18nKey": "route.500"}', 1, 1, 'admin', 1, 'admin', '2024-03-26 08:49:27', '2024-03-26 08:49:30', 0, NULL);
INSERT INTO `t_sys_resource` VALUES (26, 0, 'login', '4', '1', '登录', 'login', '/login/:module(pwd-login|code-login|register|reset-pwd|bind-wechat)?', 'layout.blank$view.login', '{"constant": true, "hideInMenu": true, "title": "login", "i18nKey": "route.login"}', 1, 1, 'admin', 1, 'admin', '2024-03-26 08:49:27', '2024-03-26 08:49:30', 0, NULL);
INSERT INTO `t_sys_resource` VALUES (27, 19, '/manage/user:add', '3', '1', '用户添加权限', 'manage:user:add', '', '', '', 1, 1, 'admin', 1, 'admin', '2024-04-01 08:49:27', '2024-04-04 03:29:53', 0, NULL);
COMMIT;

-- ----------------------------
-- 表结构: t_sys_role 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色code',
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态；1:可用，2:禁用',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `type` tinyint NOT NULL COMMENT '类型：1:公共角色；2:特殊角色',
  `create_id` bigint NOT NULL COMMENT '创建者ID',
  `create_by` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '创建者名称',
  `update_id` bigint NOT NULL COMMENT '修改者ID',
  `update_by` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '修改者名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否已删除：0->未删除；1->已删除',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色表';

-- ----------------------------
-- 表记录: t_sys_role 
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES (1, '超级管理员', 'SUPBER_ADMIN', '1', '权限超级大，拥有所有权限', 2, 1, 'admin', 1, 'admin', '2024-03-09 10:21:23', '2024-03-09 10:21:25', 0, NULL);
INSERT INTO `t_sys_role` VALUES (2, '普通管理员', 'ADMIN', '1', '只拥有部分管理权限', 2, 1, 'admin', 1, 'admin', '2024-03-09 10:21:23', '2024-03-09 10:21:25', 0, NULL);
COMMIT;

-- ----------------------------
-- 表结构: t_sys_role_resource 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_resource`;
CREATE TABLE `t_sys_role_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `resource_id` bigint NOT NULL,
  `create_id` bigint unsigned DEFAULT NULL,
  `create_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_by` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=718 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色资源关联表';

-- ----------------------------
-- 表记录: t_sys_role_resource 
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role_resource` VALUES (1, 1, 1, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (2, 1, 2, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (3, 1, 3, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (4, 1, 4, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (5, 1, 5, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (6, 1, 6, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (7, 1, 7, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (8, 1, 8, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (9, 1, 9, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (10, 1, 10, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (11, 1, 11, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (12, 1, 12, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (14, 1, 14, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (15, 1, 15, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (16, 1, 16, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (17, 1, 17, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (18, 1, 18, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (19, 1, 19, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (20, 1, 20, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (21, 1, 21, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (22, 1, 22, 1, 'admin', 1, 'admin', '2024-03-11 10:24:29', NULL, 0, NULL);
INSERT INTO `t_sys_role_resource` VALUES (23, 1, 27, 1, 'admin', 1, 'admin', '2024-04-01 10:24:29', NULL, 0, NULL);
COMMIT;

-- ----------------------------
-- 表结构: t_sys_user 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '账户昵称',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '密码',
  `status` varchar(1) COLLATE utf8mb4_unicode_520_ci NOT NULL COMMENT '状态；1:可用，2:禁用',
  `otp_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `user_gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '性别',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '电话',
  `user_email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL COMMENT '电子邮箱',
  `last_login_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `create_id` bigint DEFAULT NULL,
  `create_by` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint DEFAULT NULL,
  `update_by` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci COMMENT='用户表';

-- ----------------------------
-- 表记录: t_sys_user 
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES (1, '管理员', 'admin', '$2a$10$qbdPPGSnLm2oQwgLXyX8wOTgVZLHnm2pqS.We5.n6do3YfVxobCUy', '1', '', '1', '13189770694', 'abc@qq.com', '2024-03-10 11:42:46', '192.168.31.51', 1, 'admin', '2020-01-01 19:00:00', 1, 'admin', '2024-03-10 11:42:46', 0, NULL);
INSERT INTO `t_sys_user` VALUES (2, 'Soybean', 'Soybean', '$2a$10$qbdPPGSnLm2oQwgLXyX8wOTgVZLHnm2pqS.We5.n6do3YfVxobCUy', '1', NULL, '1', '13892700749', '123@qq.com', '2024-04-09 22:49:09', '192.168.2.128', 1, 'admin', '2024-03-09 21:56:34', NULL, NULL, '2024-04-09 22:49:09', 0, NULL);
COMMIT;

-- ----------------------------
-- 表结构: t_sys_user_role 
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `creator_id` bigint unsigned DEFAULT NULL,
  `creator_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `update_id` bigint unsigned DEFAULT NULL,
  `update_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色关联表';

-- ----------------------------
-- 表记录: t_sys_user_role 
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user_role` VALUES (1, 1, 1, 1, 'admin', 1, 'admin', '2024-03-09 10:37:52', '2024-03-09 10:38:04', 0, NULL);
INSERT INTO `t_sys_user_role` VALUES (2, 2, 1, 1, 'admin', 1, 'admin', '2024-03-10 13:10:39', '2024-03-10 13:10:41', 0, NULL);
COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
