package com.wx.wxlogin.dao;


import com.wx.wxlogin.entity.SysUser;

public interface UserMaper {

    SysUser getUserByName(String username);
}
