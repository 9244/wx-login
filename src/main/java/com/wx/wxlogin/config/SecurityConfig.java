package com.wx.wxlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/","/login").permitAll()  // 访问 /  /login 不需要权限
                .antMatchers("/adminList").hasAnyRole("root","admin") // 访问 adminList 需要 admin root 权限
                .anyRequest().authenticated(); // 其他页面需要登陆之后才能访问
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("")
                .authoritiesByUsernameQuery("")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //密码加密
    public String encodePassword(String password){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode(password);
        return encode;
    }
}