package com.wx.wxlogin.thread;

import com.wx.wxlogin.task.taskExceptionHandller.TaskExceptionHandler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池工具类
 * @author: WXxxx
 * @time: 2021/8/15 20:36
 */
public class ThreadPoolUtil {

    /**
     * 线程池初始值设置
     * corePoolSize: 基本大小
     * maximunPoolSize: 最大大小
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor( 10,15,15,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>());

    /**
    * @Auther WXxx
    * @data  21:02
    * @Deseription: 设置 reject policy
    * @Param null
    * @throws
    */
    private static void setRejectExecution(){
        ThreadPoolUtil.threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    }


    public ThreadPoolUtil(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

}
