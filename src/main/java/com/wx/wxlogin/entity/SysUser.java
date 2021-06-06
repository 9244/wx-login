package com.wx.wxlogin.entity;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/6/6 0:08
 */
@Data
public class SysUser {

    private String id;

    private String userName;

    private String password;

    private List<String> role;

    private String encodePassword;

    private String eMail;

    private String mobile;


}
