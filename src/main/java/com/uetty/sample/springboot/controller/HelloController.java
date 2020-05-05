package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.service.LoginService;
import com.uetty.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${server.apiUrlPrefix}/")
public class HelloController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "hello")
    public BaseResponse<String> hello() {
        return successResult("hello world!");
    }

    @RequestMapping(value = "getUser")
    public BaseResponse<User> getUser(String username) {

        User user = userService.getUserByUsername(username);

        return successResult(user);
    }

    @RequestMapping(value = "getUsers")
    public BaseResponse<PagedResponseData<User>> getUsers() {
        Page<User> page = userService.getPageUsers();
        PagedResponseData<User> pagedData = getPagedData(page);
        return successResult(pagedData);
    }

    @RequestMapping(value = "login")
    public BaseResponse<User> login(String username, String password) {

        return successResult(loginService.login(username, password));
    }

    @RequestMapping(value = "logout")
    public BaseResponse<User> login() {
        loginService.logout();
        return successResult();
    }

}
