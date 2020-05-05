package com.uetty.sample.springboot.service;

import com.uetty.sample.springboot.entity.User;

public interface LoginService {
    User login(String username, String password);

    void logout();
}
