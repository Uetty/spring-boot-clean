package com.uetty.sample.springboot.dao;

import com.github.pagehelper.Page;
import com.uetty.sample.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User getById(String id);

    User getByUsername(String userName);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(String id);

    Page<User> getAllUser();

}