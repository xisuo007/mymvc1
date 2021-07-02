package com.king.thread.交替打印数字和字母2;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by ljq on 2020/3/30 9:35
 */
public class Blocking_Queue {
    static Thread t1 = null,t2 = null;
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();
        ArrayBlockingQueue<Character> queue = new ArrayBlockingQueue(1);
        ArrayBlockingQueue<Character> queue2 = new ArrayBlockingQueue(1);
        t1 = new Thread(()->{
            for (char c : c1) {
                try {
                    char take = queue.take();
                    System.out.println(take);
                    queue2.put(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c : c2) {
                try {
                    queue.put(c);
                    Character take = queue2.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
