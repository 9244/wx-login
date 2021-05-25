package com.wx.wxlogin.service.impl;

import com.wx.wxlogin.dao.LoginMapper;
import com.wx.wxlogin.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Override
    public Integer getUserCount() {
        return loginMapper.getUserCount();
    }
}
