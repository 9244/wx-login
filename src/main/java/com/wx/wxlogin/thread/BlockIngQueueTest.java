package com.wx.wxlogin.thread;

import javafx.concurrent.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/9/25 17:33
 */
public class BlockIngQueueTest {

    private BlockingQueue<String> blockingQueue;

    public BlockIngQueueTest(ArrayBlockingQueue<String> strings) {
        this.blockingQueue = strings;
    }

    private String getNextTask() throws InterruptedException {
        return blockingQueue.take();
    }


    public static void main(String[] args) throws InterruptedException {
        BlockIngQueueTest blockIngQueueTest = new BlockIngQueueTest(new ArrayBlockingQueue<String>(2));
        String nextTask = blockIngQueueTest.getNextTask();
    }
}
