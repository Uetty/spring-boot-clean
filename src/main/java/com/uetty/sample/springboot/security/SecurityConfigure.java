package com.uetty.sample.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


@EnableWebSecurity
@AutoConfigureAfter(SecurityConfigure.SecurityBeanConfigure.class)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Configurable
    static class SecurityBeanConfigure {
        /**
         * 这里为security指定一个加密方式（这个加密目的仅是内存安全，数据库里最好还是要单独进行md5加密）
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /**
         * 声明CsrfTokenRepository的Bean
         */
        @Bean
        public CsrfTokenRepository csrfTokenRepository() {
            return new HttpSessionCsrfTokenRepository();
        }
    }

    @Value("${server.apiUrlPrefix}/login")
    private String loginProcessingUrl;
    @Value("${server.apiUrlPrefix}/**")
    private String apiPath;
    @Value("${server.apiUrlPrefix}/logout")
    private String logoutPath;
    @Value("${server.apiUrlPrefix}/generateCsrf")
    private String generateCsrfPath;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 声明使用的csrfTokenRepository
        http.csrf().csrfTokenRepository(csrfTokenRepository);
        http
                .authorizeRequests()
                // 允许未登录下访问获取csrf token的接口
                .antMatchers(generateCsrfPath).permitAll()
                // api接口都需要登录
                .antMatchers(apiPath).authenticated()
                // 其余路径（页面）不需要认证
                .anyRequest().permitAll()

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
                .and()
                .logout()
                .logoutUrl(logoutPath)
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义获取用户信息的业务类
        auth.userDetailsService(userDetailsService);
    }
}
