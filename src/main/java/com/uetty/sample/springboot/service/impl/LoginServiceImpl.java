package com.uetty.sample.springboot.service.impl;

import com.uetty.sample.springboot.constant.ResponseCode;
import com.uetty.sample.springboot.dao.UserDao;
import com.uetty.sample.springboot.entity.User;
import com.uetty.sample.springboot.exception.BusinessException;
import com.uetty.sample.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    HttpServletRequest httpServletRequest;

    AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            throw new BusinessException(ResponseCode.LOGIN_FAILED);
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(),
                user.getAuthorities());
        result.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(result);
        return user;
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
