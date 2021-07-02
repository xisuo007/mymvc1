package com.king.thread.交替打印数字和字母2;

/**
 * Created by ljq on 2020/1/17 9:22
 */
public class Wait_Notify {
    static Thread t1 = null, t2 = null;
    static boolean t1Run = false;

    public static void main(String[] args) {
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                while (!t1Run) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char c : c1) {
                    System.out.println(c);
                    t1Run = false;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                while (t1Run) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char c : c2) {
                    System.out.println(c);
                    t1Run = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();
    }
}
