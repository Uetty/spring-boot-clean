package com.uetty.sample.springboot.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@AutoConfigureAfter(SecurityConfigure.SecurityBeanConfigure.class)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Configurable
    static class SecurityBeanConfigure {

    }

    @Value("${server.apiUrlPrefix}/login")
    private String loginProcessingUrl;
    @Value("${server.apiUrlPrefix}/**")
    private String apiPath;
    @Value("${server.apiUrlPrefix}/logout")
    private String logoutPath;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                // api接口都需要登录
                .antMatchers(apiPath).authenticated()
                // 其余路径（页面）不需要认证
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                ;
    }
}
