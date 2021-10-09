package com.wx.wxlogin.task;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/8/5 21:27
 */
public class TestTask extends Thread {

    private String name;

    public TestTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("---------");
        System.out.println(3/2);
        System.out.println(3/0);
    }
}
