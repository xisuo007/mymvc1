package com.king.demo.ReentrantLockTest;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by ljq on 2019/3/12 17:32
 * CyclicBarrier 中文名叫做屏障或者是栅栏，也可以用于线程间通信。

 它可以等待 N 个线程都达到某个状态后继续运行的效果。

 首先初始化线程参与者。
 调用 await() 将会在所有参与者线程都调用之前等待。
 直到所有参与者都调用了 await() 后，所有线程从 await() 返回继续后续逻辑。
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws Exception{
        new CyclicBarrierTest().cyclicBarrier();
    }
    private static void cyclicBarrier() throws Exception{
        final CyclicBarrier cy = new CyclicBarrier(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"：  线程  run");
                try {
                    cy.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":  线程跑完了");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"：  线程  run");
                try {
                    cy.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":  线程跑完了");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"：  线程  run");
                try {
                    Thread.sleep(5000);
                    cy.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()+":  线程跑完了");
            }
        }).start();

        System.out.println("main thread");
    }
}
