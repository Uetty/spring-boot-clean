package com.uetty.sample.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Autowired
    AuthenticationProviderImpl authenticationProvider;

    @Autowired
    SecurityExceptionHandler exceptionHandler;
    @Autowired
    AuthenticationHandler authenticationHandler;

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
                .logout()
                .logoutUrl(logoutPath)
                .and()
                .formLogin()
                // 登录页面地址（也即判断请求未登录验证时，重定向到的页面）
                .loginPage("/login")
                // 登录接口路径
                .loginProcessingUrl(loginProcessingUrl)
                // 登录成功后重定向到的页面地址
                .defaultSuccessUrl("/index")
                // 登录失败时重定向到的页面
                .failureUrl("/error")
                // 登录接口传递用户名的参数名
                .usernameParameter("username")
                // 登录接口传递密码的参数名
                .passwordParameter("password")

                // 登录成功和登录失败的处理
                .failureHandler(authenticationHandler)
                .successHandler(authenticationHandler)
                // 无权限的处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(exceptionHandler)
                .authenticationEntryPoint(exceptionHandler);
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义AuthenticationProvider
        auth.authenticationProvider(authenticationProvider);
    }
}
