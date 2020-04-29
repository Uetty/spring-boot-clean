package com.uetty.sample.springboot.service;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.entity.User;

public interface UserService {
    User getUserByUsername(String username);

    Page<User> getPageUsers();
}
