package com.pea.component.security.handle;

import cn.hutool.json.JSONUtil;
import com.pea.common.api.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 当访问接口没有权限时，自定义的返回结果
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Result.forbidden(accessDeniedException.getMessage())));
        response.getWriter().flush();
    }
}
