package com.uetty.sample.springboot.security;

import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.ResponseCode;
import com.uetty.sample.springboot.controller.BaseController;
import com.uetty.sample.springboot.util.JacksonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功/失败时的响应处理
 */
@Component
public class AuthenticationHandler extends BaseController implements AuthenticationFailureHandler, AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String requestURI = httpServletRequest.getRequestURI();
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        BaseResponse<Object> objectBaseResponse = errorResult(ResponseCode.LOGIN_FAILED);
        String out = JacksonUtil.jackson.obj2Json(objectBaseResponse);
        httpServletResponse.getWriter().print(out);
        httpServletResponse.flushBuffer();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String requestURI = httpServletRequest.getRequestURI();
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        BaseResponse<Object> objectBaseResponse = successResult();
        String out = JacksonUtil.jackson.obj2Json(objectBaseResponse);
        httpServletResponse.getWriter().print(out);
        httpServletResponse.flushBuffer();
    }
}
