package com.matzipuniv.sinchon.config;

import com.matzipuniv.sinchon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests() //url별 권한 관리 설정
                .antMatchers("api/**").permitAll()
                .anyRequest().permitAll() // 나머지들은, 로그인한 사람들만
                .and()
                .logout()
                .logoutSuccessUrl("/") //로그아웃 성공시 여기로 이동
                .and()
                .oauth2Login() //OAuth2 로그인 기능 설정
                .userInfoEndpoint() //로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
                .userService(userService); //UserService 인터페이스의 구현체
    }
}