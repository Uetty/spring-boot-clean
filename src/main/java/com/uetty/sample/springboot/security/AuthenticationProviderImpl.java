package com.uetty.sample.springboot.security;

import com.uetty.sample.springboot.dao.UserDao;
import com.uetty.sample.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class AuthenticationProviderImpl extends AbstractUserDetailsAuthenticationProvider {

    // 限制登录频率
    private static final int MAX_FAILED_TIMES = 5;
    private static final long FAILED_INTERVAL = 120_000L;

    @Autowired
    UserDao userDao;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 校验密码逻辑
        String password = authentication.getCredentials().toString();
        User user = (User) userDetails;
        Integer loginFailedTimes = user.getLoginFailedTimes();
        loginFailedTimes = loginFailedTimes == null ? 0 : loginFailedTimes;
        long lastLoginTime = user.getLastLoginTime() != null ? user.getLastLoginTime().getTime() : 0L;
        Date date = new Date();
        // 限制登录失败频率
        if (loginFailedTimes >= MAX_FAILED_TIMES
                && date.getTime() - lastLoginTime < FAILED_INTERVAL) {
            throw new BadCredentialsException("high login frequency"); // 登录频率过高
        }

        boolean passwordValid = Objects.equals(password, user.getPassword());
        // 更新登录成功和失败信息
        user.setLoginFailedTimes(passwordValid ? 0 : loginFailedTimes + 1);
        user.setLastLoginTime(date);
        user.setUpdateTime(date);
        userDao.update(user);
        if (!passwordValid) {
            throw new BadCredentialsException("login failed");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            User user = userDao.getByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("username[" + username + "] not found");
            }
            return user;
        } catch (UsernameNotFoundException ne) {
            throw ne;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

}
