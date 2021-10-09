package com.wx.wxlogin.task.taskExceptionHandller;

import com.wx.wxlogin.task.TestTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 用于监测多线程出错打印日志
 * @author: WXxxx
 * @time: 2021/8/5 21:20
 */
@Slf4j
public class TaskExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.info(" 线程出错--" + t.getName() + "\n" + e.getMessage());
    }

    public static void main(String[] args) {
        try {
            TestTask t1 = new TestTask("测试线程1");
            t1.setUncaughtExceptionHandler(new TaskExceptionHandler());
            t1.start();
        }catch (Exception e){
            System.out.println("主线程捕获异常" + e.getMessage());
        }
    }
}
