package com.wx.wxlogin.thread;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/9/12 19:53
 */
public class TestThreadYield extends Thread{

    private String name;

    public TestThreadYield(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i <30 ; i++) {
            System.out.println(name + "------" + i);
            if (i == 15){
                this.yield();
            }
        }
    }

    public static void main(String[] args) {
        TestThreadYield t1 = new TestThreadYield("张三");
        TestThreadYield t2 = new TestThreadYield("李四");

        t1.start();
        t2.start();

    }
}
