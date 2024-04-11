package com.pea.common.config;

import com.pea.component.security.filter.JwtAuthenticationTokenFilter;
import com.pea.component.security.handle.RestAuthenticationEntryPoint;
import com.pea.component.security.handle.RestfulAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * 自定义用户认证逻辑
     */
    final UserDetailsService userDetailsService;

    final RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 基于token，所以不需要session
                .sessionManagement(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(author -> author
                        // 允许对于网站静态资源的无授权访问
                        .requestMatchers(
                                "/webjars/**",
                                "/doc.html",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources",
                                "/swagger-ui.html")
                        .permitAll()

                        // 允许匿名访问
                        .requestMatchers("/auth/login", "/route/getConstantRoutes").permitAll()

                        .anyRequest().authenticated());

        // 禁用缓存
        http.headers(headers -> headers
                .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
        );

        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
        );

        // 注入authenticationManager
        http.authenticationManager(authenticationManager());

        return http.build();
    }

    /**
     * 身份认证接口 构造一个AuthenticationManager，使用自定义的userDetailsService和passwordEncoder
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
