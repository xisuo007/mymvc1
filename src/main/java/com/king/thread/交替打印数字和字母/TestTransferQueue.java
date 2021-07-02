package com.king.thread.交替打印数字和字母;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by ljq on 2020/1/16 15:18
 */
public class TestTransferQueue {

    static Thread t1 = null,t2 = null;
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        //线程池中也较常用     可传递队列，容量为0
        TransferQueue<Character> queue = new LinkedTransferQueue<>();

        new Thread(()->{
            try {
                for (char c : c1) {
                    System.out.println(queue.take());//去queue里取一个，如果没有就等着
                    queue.transfer(c);//往queue里放一个
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        //相当于手递手，而且里面只能放一个，顺序取决于谁先放进去
        new Thread(()->{
            try {
                for (char c : c2) {
                    queue.transfer(c);//往queue里放一个
                    System.out.println(queue.take());//去queue里取一个，如果没有就等着
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
