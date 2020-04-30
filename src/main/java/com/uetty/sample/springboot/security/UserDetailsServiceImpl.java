package com.uetty.sample.springboot.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uetty.sample.springboot.dao.UserDao;
import com.uetty.sample.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Lists;

import java.util.ArrayList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username[" + username + "] not found");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        // 可以让自定义的user实现UserDetails接口，就不用再去new这个springframework的User
        // 当自定义的user要注意不能让password等敏感信息泄漏给前端
        // 可以再实现CredentialsContainer接口擦除敏感信息，或者在敏感信息上使用@JsonIgnore注解
        return new org.springframework.security.core.userdetails.User(username,
                passwordEncoder.encode(user.getPassword()), authorities);
    }
}
