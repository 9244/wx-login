package com.wx.wxlogin.util;

import java.util.concurrent.*;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/6/16 19:45
 */
public class FutureCancel {

    // 定长线程池
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void cancelExecutor(){
        Callable<Object> c = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                // do something
                Thread.sleep(10000);
                System.out.println("111");
                return "finished";
            }
        };

        Future<Object> submit = executor.submit(c);
        try {
            Object o = submit.get(5, TimeUnit.SECONDS);
            System.out.println(o.toString());
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            submit.cancel(true);
        }

    }

    /**
     *  线程获取数据到达一定时间之后返回 TimeOutException
     *  取消获取
     *  关闭线程池
     * @param args
     */
    public static void main(String[] args) {
        FutureCancel.cancelExecutor();
    }
}
