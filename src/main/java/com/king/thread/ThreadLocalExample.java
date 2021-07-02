package com.king.thread;

/**
 * Created by ljq on 2020/3/6 16:55
 * 每个 Thread 都有一个 ThreadLocal.ThreadLocalMap 对象。
 * 当调用一个 ThreadLocal 的 set(T value) 方法时，先得到当前线程的 ThreadLocalMap 对象，然后将 ThreadLocal->value 键值对插入到该 Map 中。
 */
public class ThreadLocalExample {
    public static void main(String[] args){
        ThreadLocal local1 = new ThreadLocal();
        ThreadLocal local2 = new ThreadLocal();

        Thread thread1 = new Thread(() -> {
            local1.set(1);
            local1.set(3);
            local2.set(1);
            System.out.println(local1.get());
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(local1.get());
            local1.set(2);
            local2.set(2);
        });
        thread1.start();
        thread2.start();
    }

}
