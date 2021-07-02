package com.king.thread.交替打印数字和字母2;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by ljq on 2020/1/17 10:22
 */
public class Transfer_Queue {

    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        TransferQueue queue = new LinkedTransferQueue();//此队列为可传递队列，里面的排队个数是0

        new Thread(()->{
            for (char c : c1) {
                try {
                    //去queue里取一个，没有就阻塞
                    System.out.println(queue.take());
                    queue.transfer(c);//放一个值到queue里，如果里面有就阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            for (char c : c2) {
                try {
                    queue.transfer(c);//放一个值到queue里，如果里面有就阻塞
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();
    }
}
