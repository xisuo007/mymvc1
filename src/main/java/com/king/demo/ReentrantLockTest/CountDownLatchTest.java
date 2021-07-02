package com.king.demo.ReentrantLockTest;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ljq on 2019/3/12 17:17
 * 初始化一个 CountDownLatch 时告诉并发的线程，然后在每个线程处理完毕之后调用 countDown() 方法。
 该方法会将 AQS 内置的一个 state 状态 -1 。
 最终在主线程调用 await() 方法，它会阻塞直到 state == 0 的时候返回。
 */
public class CountDownLatchTest{
    public static void main(String[] args) throws Exception{
        CountDownLatchTest test = new CountDownLatchTest();
        test.countDownLath();
    }
    private static void countDownLath() throws Exception{
        final int thread = 3;
        long start = System.currentTimeMillis();
        final CountDownLatch count = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"thread run --------");
                    try {
                        Thread.sleep(2000);
                        count.countDown();
                        System.out.println("thread end======");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        count.await();
        long stop = System.currentTimeMillis();
        System.out.println("主线程用时"+(stop-start));
    }
}
