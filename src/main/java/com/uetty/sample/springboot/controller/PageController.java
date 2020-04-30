package com.uetty.sample.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

/**
 * 简陋的作为页面的controller
 */
@Controller
public class PageController extends BaseController {

    @RequestMapping("{pageName}")
    public String getPage(@PathParam("pageName") String pageName) {
        return pageName;
    }
}
