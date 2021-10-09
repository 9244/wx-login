package com.wx.wxlogin.test;

import com.wx.wxlogin.entity.Student;
import com.wx.wxlogin.task.TestTask;
import com.wx.wxlogin.task.taskExceptionHandller.TaskExceptionHandler;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class TestController {


    @Test
    public void test1(){
        Student student = new Student();
        Student student1 = new Student();

        student.setName("wxx");
        student.setAge(19);

        student1.setName("lll");
        student1.setAge(22);

        List<Student> list = new ArrayList<Student>();
        list.stream().sorted((x1,x2) -> x1.getAge() -x2.getAge()).collect(Collectors.toList());
    }

    @Test
    public void test2(){
        ExecutorService executorService = Executors.newFixedThreadPool(10,r -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new TaskExceptionHandler());
            return thread;
        });
        executorService.execute(new TestTask("---"));
        Thread.interrupted(); // 静态方法 返回 当前线程的状态， 并标记清除
        Thread.currentThread().isInterrupted(); // 返回调用线程的中断状态
//        Thread.join()
    }

    @Test
    public <T> void test3() throws InterruptedException {
//        Queue queue = new PriorityQueue();
//        BlockingQueue queue1 = new ArrayBlockingQueue(10);
//        queue1.take();
//        queue1.poll();
//        ConcurrentLinkedQueue queue2 = new ConcurrentLinkedQueue();
//        queue2.add("");
//        List<String> list = new ArrayList<>();
//        list.add("1");
//
//        LinkedList<String> linkedList = new LinkedList<>();
//        linkedList.add("");
//
        ConcurrentHashMap<String,Object> hashMap = new ConcurrentHashMap<String, Object>();
        hashMap.size();
        hashMap.put("","");

        HashMap  map = new HashMap();
        map.put("","");
//
//        ReadWriteLock readLock = new ReentrantReadWriteLock();
//        Lock lock = readLock.readLock();
//        Lock lock1 = readLock.writeLock();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int andSet = atomicInteger.getAndSet(2);
        System.out.println(andSet);
        System.out.println(atomicInteger.get());
//        atomicInteger.compareAndSet();
    }
}
