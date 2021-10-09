package com.wx.wxlogin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public void test2(){
        Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        set.add("");

         Thread.currentThread().isInterrupted();
         Thread.interrupted();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            executorService.awaitTermination(20,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void test3() throws Exception {

        ArrayList<String> list = new ArrayList<>();

//        list.add(2);
       Class aClass = list.getClass();
        Method add =  aClass.getMethod("add",Object.class);
        add.invoke(list,1);
        System.out.println(list.get(0));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.awaitTermination(10,TimeUnit.SECONDS);

        Semaphore semaphore = new Semaphore(10);
        semaphore.tryAcquire(1,TimeUnit.SECONDS);

        Lock lock = new ReentrantLock(true);
        lock.tryLock();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.getAndIncrement();

        ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                return "ss";
            }
        };

        AtomicReference<String> atomicReference = new AtomicReference<>();
    }

    @org.junit.Test
    public void test4() throws InterruptedException {
       ExecutorService executorService = Executors.newFixedThreadPool(10);
       Thread.currentThread().interrupt();
       Thread.currentThread().isInterrupted();

        Future<String> submit = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        try {
            submit.get(100,TimeUnit.SECONDS);
        } catch (ExecutionException e) {
//            程序异常
            e.printStackTrace();
//            e.getCause() 获取原因
            System.out.println(e.getCause());
        } catch (TimeoutException e) {
//            任务超时0 中断
            submit.cancel(true);
            e.printStackTrace();
        }


        executorService.invokeAll(new ArrayList<>());


//        Future<?> submit = executorService.submit(new Runnable() {
//            @Override
//            public void run() {
////
//            }
//        });
//        try {
//            submit.get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

    }

    @org.junit.Test
    public void test5(){
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.incrementAndGet();
        System.out.println(i);

        AtomicInteger atomicInteger1 = new AtomicInteger();
        int andIncrement = atomicInteger1.getAndIncrement();
        System.out.println(andIncrement);

        System.identityHashCode("dd");


        String a = "aaa";
        String b = "aaa";
        System.out.println( a == b);
    }
}
