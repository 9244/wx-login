package com.wx.wxlogin.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/6/15 19:59
 */
public class Test {

    public void test1() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        Thread thread = new Thread(futureTask);
        thread.start();
        futureTask.get();
    }
}
