package com.uetty.sample.springboot.controller;

import com.uetty.sample.springboot.constant.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController {

    @RequestMapping(value = "hello")
    public BaseResponse<String> hello() {
        return successResult("hello world!");
    }
}
