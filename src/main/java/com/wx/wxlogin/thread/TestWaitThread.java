package com.wx.wxlogin.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/8/15 15:38
 */
public class TestWaitThread implements Runnable {

    private List<String> list = new ArrayList<>();

    private Integer num;

    public TestWaitThread(Integer num) {
        this.num = num;
    }

    @Override
    public void run() {
        try {
            System.out.println("测试wait" + Thread.currentThread().getName());
           synchronized (list){
               if (num%2 == 0){
                   System.out.println(num + "---------------");
                   list.wait();
               }else {
                   list.notify();
                   System.out.println(num + "notify");
               }
           }
            System.out.println("wait 结束" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("捕获异常" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int j = random.nextInt(10);
            TestWaitThread t1 = new TestWaitThread(j);
            Thread thread = new Thread(t1);
            thread.start();
            Thread.yield();
        }
    }


}
