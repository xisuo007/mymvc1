package com.king.thread.交替打印数字和字母;

/**
 * Created by ljq on 2020/1/16 14:59
 * 相当于生产者消费者一样，需要先唤醒，再等待(释放锁)，循环完毕后需要再次互相唤醒，否则会进入等待状态，不能停止！
 * 要控制顺序需要指定一个标识，循环判断值来控制是否继续执行
 */
public class WaitNotify {
    static boolean t1run = true;
    static Thread t1 = null,t2 = null;
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();
        final Object o = new Object();
        t1 = new Thread(()->{
            synchronized (o){
                while (!t1run) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char c : c1) {
                    System.out.println(c);
                    t1run = false;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t1");

        t2 = new Thread(()->{
            synchronized (o){
                while (t1run) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char c : c2) {
                    System.out.println(c);
                    t1run = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
