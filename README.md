# 豌豆

## 简介

项目采用SpringBoot3.2 +
JDK21、MyBatis-Plus、SpringSecurity安全框架等，适配 [soybean-admin](https://gitee.com/honghuangdc/soybean-admin)
开发的简单权限系统。

## **技术选型：**

| 依赖           | 版本     |
|--------------|--------|
| Spring Boot  | 3.2.4  |
| JDK          | 21     |
| Mybatis-Plus | 3.5.5  |
| hutool       | 5.8.25 |
| knife4j      | 4.5.0  |
| jwt          | 0.9.1  |
| mysql        | 8.0.33 |
| ...          | ...    |

## TODO

- [ ] 优化补充菜单
- [ ] 优化日志管理
- [ ] 开发实现Google二次认证

## 后端部署

> - **GitHub仓库地址:** https://github.com/haitang1894/pea.git

- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库pea，数据库编码为UTF-8
- 执行doc/sql/pea.sql文件，初始化数据
- 修改application-local.yml，更新MySQL账号和密码
- Eclipse、IDEA运行PeaApplication.java，则可启动项目
- Swagger注解路径：http://localhost:9528/doc.html

## 前端部署

> - **GitHub仓库地址:**  https://github.com/soybeanjs/soybean-admin.git

- 前端部署以及更换访问路径请看下面文档


- 前端部署方案：请参考 **[soybean-admin](https://docs.soybeanjs.cn/zh/)** 项目文档

- 前端部署完毕，修改配置就可以使用该后端

- 账号：Soybean，密码：123456

- 账号：admin，密码：admin123.

## 注解

- 日志记录注解 @SysLogInterface
- 权限认证注解 @PreAuthorize("@pre.hasPermission('system:user:add')")
  目前 权限认证注解 开发并不完善,考虑前端暂未实现权限校验,没有进一步开发
