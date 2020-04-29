package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.jms.JmsProducers;
import com.uetty.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/${server.apiUrlPrefix}/")
public class HelloController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    JmsProducers jmsProducers;

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

    @RequestMapping(value = "sendMessage")
    public BaseResponse sendJmsMessage(String message) {
        try {
            jmsProducers.sendToTestQueue(message, 0);
        } catch (JMSException e) {
            return errorResult(e.getMessage());
        }
        return successResult();
    }
}
