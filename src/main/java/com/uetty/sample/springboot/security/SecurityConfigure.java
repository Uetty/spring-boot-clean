package com.uetty.sample.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;


@EnableWebSecurity
@AutoConfigureAfter(SecurityConfigure.SecurityBeanConfigure.class)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Configurable
    static class SecurityBeanConfigure {
        @Bean
        public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
            return new InMemoryClientRegistrationRepository(clientRegistration);
        }

        @Bean
        private ClientRegistration githubClientRegistration() {
            return ClientRegistration.withRegistrationId("github")
                    .clientId("your client id")
                    .clientSecret("your client secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                    .scope("repo", "public_repo", "user")
                    .authorizationUri("https://github.com/login/oauth/authorize")
                    .tokenUri("https://github.com/login/oauth/access_token")
                    .userInfoUri("https://api.github.com/user")
                    .clientName("github-client")
                    // 取userInfoUri接口返回值中的email作为userName
                    .userNameAttributeName("email")
//                    .userNameAttributeName("id")
//                    .userNameAttributeName("login")
                    .build();
        }
    }

    @Value("${server.apiUrlPrefix}/login")
    private String loginProcessingUrl;
    @Value("${server.apiUrlPrefix}/**")
    private String apiPath;
    @Value("${server.apiUrlPrefix}/logout")
    private String logoutPath;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


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
                .clientRegistrationRepository(clientRegistrationRepository)
                ;
    }
}
