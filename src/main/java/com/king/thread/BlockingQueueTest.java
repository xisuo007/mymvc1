package com.king.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by ljq on 2020/3/6 14:33
 * 可以用来做生产者消费者
 */
public class BlockingQueueTest {
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(3);

        Thread t1 = new Thread(() -> {
            for (char c : c1) {
                System.out.println("生产者");
                try {
                    queue.put(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (char c : c2) {
                System.out.println("消费者");
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}
