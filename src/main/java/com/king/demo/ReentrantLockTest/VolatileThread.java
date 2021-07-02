package com.king.demo.ReentrantLockTest;

import java.util.concurrent.TimeUnit;

/**
 * Created by ljq on 2019/3/12 17:00
 */
public class VolatileThread implements Runnable {
    //因为 Java 是采用共享内存的方式进行线程通信的，所以可以采用以下方式用主线程关闭 A 线程:
    //这里的 flag 存放于主内存中，所以主线程和线程 A 都可以看到
    private static volatile boolean flag = true;
    @Override
    public void run() {
        while (flag){
            System.out.println(Thread.currentThread().getName()+"  在运行");
        }
        System.out.println(Thread.currentThread().getName()+"   停止运行了----");
    }
    public static void main(String[] args) throws InterruptedException {
        VolatileThread t = new VolatileThread();
        new Thread(t,"AA").start();
        System.out.println("main  线程正在运行");

        TimeUnit.MILLISECONDS.sleep(100);
        t.stopThread();
    }
    private void stopThread(){
        flag = false;
    }
}
