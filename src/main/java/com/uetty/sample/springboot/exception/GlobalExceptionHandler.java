package com.uetty.sample.springboot.exception;

import com.uetty.sample.springboot.constant.ResponseCode;
import com.uetty.sample.springboot.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e){

        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            ResponseCode responseCode = be.getResponseCode();
            if (responseCode != ResponseCode.INTERNAL_ERROR) {
                return errorResult(responseCode);
            }
        }

        LOG.error(e.getMessage(), e);
        return errorResult(ResponseCode.INTERNAL_ERROR);
    }

}
