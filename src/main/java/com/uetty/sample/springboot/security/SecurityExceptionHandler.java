package com.uetty.sample.springboot.security;

import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.ResponseCode;
import com.uetty.sample.springboot.controller.BaseController;
import com.uetty.sample.springboot.util.JacksonUtil;
import com.uetty.sample.springboot.util.StringUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限情况下的返回值处理
 */
@Component
public class SecurityExceptionHandler extends BaseController implements AuthenticationEntryPoint, AccessDeniedHandler {

    /**
     * 没有登录的情况下访问需要登录的接口
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException, ServletException {

        handleAccessFailed(request, response, authenticationException);
    }

    /**
     * 已登录但没有权限的情况下访问需要角色权限的接口
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        handleAccessFailed(request, response, accessDeniedException);
    }

    public void handleAccessFailed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception e) throws IOException, ServletException {

        String requestURI = httpServletRequest.getRequestURI();
        if (StringUtil.startsWith(requestURI, "/api")) { // 接口请求
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            BaseResponse<Object> objectBaseResponse = errorResult(ResponseCode.USER_HAS_NO_ACCESS);
            String out = JacksonUtil.jackson.obj2Json(objectBaseResponse);
            httpServletResponse.getWriter().print(out);

            httpServletResponse.flushBuffer();

        } else { // 页面请求
            // 重定向页面
            httpServletResponse.sendRedirect("/login");
        }
    }
}
