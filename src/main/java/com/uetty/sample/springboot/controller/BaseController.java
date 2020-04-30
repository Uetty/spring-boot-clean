package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@SuppressWarnings("SameParameterValue")
public class BaseController {

    @Autowired
    protected HttpServletRequest httpServletRequest;
    @Autowired
    protected HttpServletResponse httpServletResponse;

    /**
     * 国际化
     */
    protected Locale getLocale() {
        return httpServletRequest.getLocale();
    }
    /**
     * 获取Session
     */
    protected HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    /**
     * 错误情况下的响应结果，使用默认的错误code
     */
    protected <T> BaseResponse<T> errorResult() {
        return errorResult(ResponseCode.FAILED);
    }
    /**
     * 错误情况下的响应结果，替换默认的错误消息
     */
    protected <T> BaseResponse<T> errorResult(String msg) {
        return errorResult(ResponseCode.FAILED, msg);
    }
    /**
     * 错误情况下的响应结果，指定错误代码
     */
    protected <T> BaseResponse<T> errorResult(ResponseCode responseCode) {
        return baseResponse(responseCode, responseCode.getDefaultMessage(getLocale()));
    }
    /**
     * 错误情况下的响应结果，指定错误代码，并替换默认错误信息
     */
    protected <T> BaseResponse<T> errorResult(ResponseCode responseCode, String message) {
        return baseResponse(responseCode, message);
    }

    protected <T> BaseResponse<T> baseResponse(ResponseCode responseCode, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(responseCode.getCode());
        baseResponse.setMessage(message);
        return baseResponse;
    }

    protected <T> BaseResponse<T> successResult() {
        return baseResponse(ResponseCode.SUCCESS, ResponseCode.SUCCESS.getDefaultMessage(getLocale()));
    }

    /**
     * 成功情况下的响应结果
     */
    protected <T> BaseResponse<T> successResult(T resultData) {
        BaseResponse<T> baseDataResponse = new BaseResponse<>();
        baseDataResponse.setStatus(ResponseCode.SUCCESS.getCode());
        baseDataResponse.setMessage(ResponseCode.SUCCESS.getDefaultMessage(getLocale()));
        baseDataResponse.setData(resultData);
        return baseDataResponse;
    }

    /**
     * 分页数据封装为能够作为返回值出参的格式
     */
    protected <T> PagedResponseData<T> getPagedData(Page<T> page) {
        return new PagedResponseData<>(page);
    }
}
