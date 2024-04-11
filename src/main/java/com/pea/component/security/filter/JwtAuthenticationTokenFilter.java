package com.pea.component.security.filter;

import cn.hutool.core.date.DateUtil;
import com.pea.common.exception.GlobalExceptionEnum;
import com.pea.common.utils.ExceptionUtil;
import com.pea.common.utils.IpUtil;
import com.pea.common.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

/**
 * Jwt 过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        long startTime, endTime;

        String authHeader = request.getHeader(jwtTokenUtil.getTokenHeader());
        String username = null;

        if (authHeader != null && authHeader.startsWith(jwtTokenUtil.getTokenHead())) {
            // The part after "Bearer "
            String authToken = authHeader.substring(jwtTokenUtil.getTokenHead().length());
            // 页面上传过来的token 解析出来的username
            username = jwtTokenUtil.getUserNameFromToken(authToken);

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 从数据库中获取用户信息
                UserDetails userDetails = this.userDetailsService
                        .loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_TOKEN_EXPIRED);
                }
            }
        }

        startTime = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        endTime = System.currentTimeMillis();

        String fullUrl = request.getRequestURL().toString();
        String requestType = request.getMethod();
        formMapKey(username, fullUrl, requestType, IpUtil.getIp(request), authHeader,
                endTime, startTime);

    }

    /**
     * @param methodName  请求接口
     * @param requestType 请求类型
     * @param ip          请求IP
     * @param token       请求token
     */
    private void formMapKey(String username, String methodName, String requestType,
                            String ip, String token, long endTime, long startTime) {
        String time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
        log.debug(
                "接口信息：time:{} , url:{} ,    type:{} , ip:{} ,username:{} token:{} , cost:{}ms\n",
                time, methodName, requestType, ip, username, token,
                (endTime - startTime));
    }

}
