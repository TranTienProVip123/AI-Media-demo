package com.bezkoder.spring.thymeleaf.image.upload.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	            .antMatchers("/", "/images/**").permitAll() // Cho phép truy cập vào trang chính và tài nguyên hình ảnh mà không cần đăng nhập
	            .anyRequest().authenticated()
	        .and()
	        .formLogin() // Sử dụng form login
	            .loginPage("/custom-login-page") // Chỉ định trang đăng nhập tùy chỉnh
	            .permitAll() // Cho phép tất cả mọi người truy cập trang đăng nhập
	        .and()
	        .logout()
	            .logoutSuccessUrl("/"); // Đặt trang chuyển hướng sau khi đăng xuất
	}

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = super.loadUser(userRequest);

                // Put your logic to process the OIDC user
                return oidcUser;
            }
        };
    }
}



