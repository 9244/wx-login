package com.wx.wxlogin.thread;

import lombok.SneakyThrows;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/8/19 20:53
 */
public class TestWaitSleepThread implements Runnable {

    private volatile Integer num;


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    /**
    * @Auther WXxx
    * @data  20:31
    * @Deseription: 锁对象，以及类对象的区别
    * @Param  * @param null
    * @throws 
    */  
    @SneakyThrows
    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + "enter");
        // this  锁的为当前对象， TestWaitSleepThread,class 为类对象
        synchronized (TestWaitSleepThread.class){
            if (num%2 == 0 ){
                System.out.println("sleep" + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("sleep finish" + Thread.currentThread().getName());
                TestWaitSleepThread.class.notifyAll();
            }else {
                System.out.println("wait!!");
                TestWaitSleepThread.class.wait();
                System.out.println("wait finish");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestWaitSleepThread t = new TestWaitSleepThread();
        t.setNum(1);
        Thread t1 = new Thread(t);
//        t1.interrupt();
        Thread t2 = new Thread(t);
        t1.start();
        Thread.sleep(100);
        t.setNum(2);
        t2.start();
    }
}
