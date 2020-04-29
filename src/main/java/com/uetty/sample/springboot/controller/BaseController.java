package com.uetty.sample.springboot.controller;

import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

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
        return baseResponse(responseCode, "failed");
    }
    /**
     * 错误情况下的响应结果，指定错误代码，并替换默认错误信息
     */
    protected <T> BaseResponse<T> errorResult(ResponseCode responseCode, String message) {
        return baseResponse(responseCode, message);
    }

    private  <T> BaseResponse<T> baseResponse(ResponseCode responseCode, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(responseCode.getCode());
        baseResponse.setMessage(message);
        return baseResponse;
    }

    protected <T> BaseResponse<T> successResult() {
        return baseResponse(ResponseCode.SUCCESS, "success");
    }

    /**
     * 成功情况下的响应结果
     */
    protected <T> BaseResponse<T> successResult(T resultData) {
        BaseResponse<T> baseDataResponse = new BaseResponse<>();
        baseDataResponse.setStatus(ResponseCode.SUCCESS.getCode());
        baseDataResponse.setMessage("success");
        baseDataResponse.setData(resultData);
        return baseDataResponse;
    }

}
