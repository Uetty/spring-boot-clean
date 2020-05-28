package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${server.apiUrlPrefix}/")
@Slf4j
public class HelloController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "hello")
    public BaseResponse<String> hello() {
        return successResult("hello world!");
    }

    @RequestMapping(value = "getUser")
    public BaseResponse<User> getUser(String username) {

        User user = userService.getUserByUsername(username);
        String id = user.getId();
        log.info("test lombok generate getter and logger, getId() -> {}", id);
        return successResult(user);
    }

    @RequestMapping(value = "getUsers")
    public BaseResponse<PagedResponseData<User>> getUsers() {
        Page<User> page = userService.getPageUsers();
        PagedResponseData<User> pagedData = getPagedData(page);
        return successResult(pagedData);
    }
}
