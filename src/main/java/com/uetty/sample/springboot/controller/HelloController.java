package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.service.RedisTestService;
import com.uetty.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@SuppressWarnings({"rawtypes", "unused"})
@RestController
@RequestMapping("/${server.apiUrlPrefix}/")
public class HelloController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    RedisTestService redisTestService;

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

    @RequestMapping(value = "redisSetValue")
    public BaseResponse redisSetValue(String key, String value) {
        redisTestService.setString(key, value);
        return successResult();
    }

    @RequestMapping(value = "redisSetUser")
    public BaseResponse redisSetUser(String username) {
        User user = userService.getUserByUsername(username);
        redisTestService.setObject("user", user);
        return successResult();
    }

    @RequestMapping(value = "redisGetValue")
    public BaseResponse<String> redisGetValue(String key) {
        String string = redisTestService.getString(key);
        return successResult(string);
    }

    @RequestMapping(value = "redisGetUser")
    public BaseResponse<Object> redisGetUser() {
        Object object = redisTestService.getObject("user");
        return successResult(object);
    }

    @RequestMapping(value = "sessionSet")
    public BaseResponse sessionSet(String key, String value) {
        HttpSession httpSession = getSession();
        httpSession.setAttribute(key, value);
        return successResult();
    }

    @RequestMapping(value = "sessionGet")
    public BaseResponse<Object> sessionGet(String key) {
        HttpSession httpSession = getSession();
        Object attribute = httpSession.getAttribute(key);
        return successResult(attribute);
    }
}
