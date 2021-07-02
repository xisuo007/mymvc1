package com.king.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljq on 2020/3/6 14:03
 */
public class CountdownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        final int totalThread = 10;
        CountDownLatch downLatch = new CountDownLatch(totalThread);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < totalThread; i++) {
            service.execute(()->{
                System.out.println("线程执行");
                downLatch.countDown();
            });
        }
        downLatch.await();
        System.out.println("格栅里面得线程都执行完了");
        service.shutdown();
    }
}
