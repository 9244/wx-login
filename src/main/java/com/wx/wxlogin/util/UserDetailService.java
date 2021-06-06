package com.wx.wxlogin.util;

import com.wx.wxlogin.config.SecurityConfig;
import com.wx.wxlogin.dao.UserMaper;
import com.wx.wxlogin.entity.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/6/6 0:04
 */
@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserMaper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名从数据库获取用户信息
        SysUser sysUser = userMapper.getUserByName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 得到用户角色
        List<String> roles = sysUser.getRole();

        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        if (CollectionUtils.isNotEmpty(roles)){
            roles.stream().forEach(item -> authorities.add(new SimpleGrantedAuthority("ROLE_" + item)));
        }
        return new User(
                sysUser.getUserName(),
                // 因为数据库是明文，所以这里需加密密码
                SecurityConfig.encodePassword(sysUser.getPassword()),
                authorities
        );
    }
}
