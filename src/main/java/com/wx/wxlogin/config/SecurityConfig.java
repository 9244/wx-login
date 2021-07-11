//
//package com.wx.wxlogin.config;
//
//import com.wx.wxlogin.util.UserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private UserDetailService userDetailService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/login/*","/rabbitmq/*").permitAll()  // 访问 /  /login 不需要权限
//                .antMatchers("/adminList").hasAnyRole("root","admin") // 访问 adminList 需要 admin root 权限
//                .antMatchers(HttpMethod.POST,"/addUser","/insertUser").permitAll()
//                .anyRequest().authenticated() // 其他页面需要登陆之后才能访问
//                .and().formLogin()
//                .loginPage("/login/toLogin") // 指定登录页面 前后端分离不需要设置
//                .loginProcessingUrl("/login/toLogin")
//                .usernameParameter("username")
//                .passwordParameter("password");
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(encodePassword());
//    }
//
//    //密码加密
//    public static String encodePassword(String password){
//        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//        String encode = bc.encode(password);
//        return encode;
//    }
//
//    public static PasswordEncoder encodePassword(){
//        return new BCryptPasswordEncoder();
//    }
//}
//
