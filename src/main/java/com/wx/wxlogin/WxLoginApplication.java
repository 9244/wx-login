package com.wx.wxlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan(value = "com.wx.*.dao")
public class WxLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxLoginApplication.class, args);
    }

}
