package com.wx.wxlogin.controller;


import com.wx.wxlogin.service.LoginService;
import com.wx.wxlogin.util.EmailUtil;
import com.wx.wxlogin.util.HttpClientUtil;
import com.wx.wxlogin.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    EmailUtil emailUtil;

    @Resource
    private LoginService loginService;

    /**
     * 测试链接数据库
     * @return
     */
    @GetMapping(value = "/testConnectionSource")
    public ResultData testConnectionSource(){
        Integer count = loginService.getUserCount();
        return ResultData.ok(count);
    }


    @GetMapping(value = "/toLogin")
    public ResultData toLogin(@RequestParam(value = "userName", required = false) String userName,@RequestParam(value = "password", required = false) String password){
        return ResultData.ok();
    }

    @GetMapping(value = "/testEmail")
    public void  testEmail(){
        String message = HttpClientUtil.getOneS();
        System.out.println(message);
        try {
            emailUtil.sendMessage("我是大脸",message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
