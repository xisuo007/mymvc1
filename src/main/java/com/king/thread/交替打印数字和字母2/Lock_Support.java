package com.king.thread.交替打印数字和字母2;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by ljq on 2020/1/17 9:35
 */
public class Lock_Support {
    static Thread t1 = null,t2 = null;
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        t1 = new Thread(()->{
            for (char c : c1) {
                System.out.println(c);
                LockSupport.unpark(t2);//叫醒t2
                LockSupport.park();//阻塞当前
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c : c2) {
                LockSupport.park();//阻塞当前
                System.out.println(c);
                LockSupport.unpark(t1);//叫醒t1
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
