package com.wx.wxlogin.controller;


import com.wx.wxlogin.service.LoginService;
import com.wx.wxlogin.util.ResultData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/login")
public class LoginController {


    @Resource
    private LoginService loginService;

    /**
     * 测试链接数据库
     * @return
     */
    @GetMapping(value = "/testConnectionSource")
    @PreAuthorize(value = "hasAnyRole('admin')")
    public ResultData testConnectionSource(){
         Integer count = loginService.getUserCount();
        return ResultData.ok(count);
    }


    @GetMapping(value = "/toLogin")
    public ResultData toLogin(@RequestParam(value = "userName", required = false) String userName,@RequestParam(value = "password", required = false) String password){
        return ResultData.ok();
    }


}
