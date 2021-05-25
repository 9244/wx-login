package com.wx.wxlogin.util;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultData<T> {

    private Integer code;

    private String message;

    private T data;

    private LocalDateTime time;

    public ResultData(Integer code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public ResultData() {
        this.code = 200;
        this.message = "成功";
        this.time = LocalDateTime.now();
    }

    public ResultData(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.data = data;
    }

    public static <T> ResultData ok(T data){
        ResultData resultData = new ResultData();
        resultData.data = data;
        return resultData;
    }

    public static ResultData ok(){
        ResultData resultData = new ResultData();
        return resultData;
    }

    public static ResultData error(){
        ResultData resultData = new ResultData();
        resultData.time = LocalDateTime.now();
        resultData.code = 500;
        resultData.message = "服务异常";
        return resultData;
    }
}
