package com.king.demo.ReentrantLockTest;

/**
 * Created by ljq on 2019/3/12 17:49
 *
 * 可以采用中断线程的方式来通信，调用了 thread.interrupt() 方法其实就是将 thread 中的一个标志属性置为了 true。

 并不是说调用了该方法就可以中断线程，如果不对这个标志进行响应其实是没有什么作用(这里对这个标志进行了判断)。

 但是如果抛出了 InterruptedException 异常，该标志就会被 JVM 重置为 false。
 */
public class InterrupedTest implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("正常运行------");
        }
        System.out.println("停止运行======");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterrupedTest(),"线程A");
        thread.start();
        System.out.println("main 正在运行---");
        thread.sleep(10);
        thread.interrupt();


    }
}
