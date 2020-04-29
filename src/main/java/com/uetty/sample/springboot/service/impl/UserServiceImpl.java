package com.uetty.sample.springboot.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uetty.sample.springboot.dao.UserDao;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<User> getPageUsers() {
        PageHelper.offsetPage(0, 10);
        return userDao.getAllUser();
    }
}
