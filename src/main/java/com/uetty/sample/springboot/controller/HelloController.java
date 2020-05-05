package com.uetty.sample.springboot.controller;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.constant.BaseResponse;
import com.uetty.sample.springboot.constant.PagedResponseData;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${server.apiUrlPrefix}/")
public class HelloController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CsrfTokenRepository csrfTokenRepository;

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

    @RequestMapping(value = "generateCsrf")
    public BaseResponse<CsrfToken> generateCsrf() {
        // 加载已经保存的token
//        CsrfToken csrfToken = csrfTokenRepository.loadToken(httpServletRequest);
        // 生成token
        CsrfToken csrfToken = csrfTokenRepository.generateToken(httpServletRequest);
        // 保存生成的token
        csrfTokenRepository.saveToken(csrfToken, httpServletRequest, httpServletResponse);
        return successResult(csrfToken);
    }
}
