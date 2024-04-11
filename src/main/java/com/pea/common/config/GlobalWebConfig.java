package com.pea.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration // 导入了Spring MVC的配置注解，该类用于全局Web配置
@EnableWebMvc // 启用Spring MVC的扩展功能
public class GlobalWebConfig implements WebMvcConfigurer {

    // 重写WebMvcConfigurer接口中的addCorsMappings方法，为跨域资源共享（CORS）进行配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加一个全局映射规则，允许所有域名（ "*" ）访问，并且允许携带认证信息（cookies等），即允许跨域请求包含凭据
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 允许任何源发起的请求
                .allowCredentials(true) // 允许跨域请求带有验证信息（cookies）

                // 允许所有的请求头通过
                .allowedHeaders("*")

                // 允许指定的HTTP方法：GET, POST, PUT, DELETE, OPTIONS
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                // 预检请求的有效期为3600秒，在此期间内浏览器无需再发送预检请求
                .maxAge(3600);
    }

    // 重写WebMvcConfigurer接口中的addResourceHandlers方法，用于处理静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将"/doc.html"路径映射到"classpath:/META-INF/resources/"下的静态资源文件
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 将"/webjars/**"路径映射到"classpath:/META-INF/resources/webjars/"下的静态资源文件，通常用于前端依赖库（如jQuery、Bootstrap等）
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}