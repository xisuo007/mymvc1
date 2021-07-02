package com.king.thread.交替打印数字和字母;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljq on 2020/1/16 15:25
 */
public class Lock_Condition {

    static Thread t1 = null,t2 = null;
    public static void main(String[] args){
        char[] c1 = "123456".toCharArray();
        char[] c2 = "ABCDEF".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();//condition相当于队列来理解，叫醒的时候可以指定队列，相当于粒度更小比syn
        Condition condition2 = lock.newCondition();

        //注意循环结束要叫醒对方，注意在finally中释放锁
        //顺序取决于谁先打印，
        new Thread(()->{
            try {
                lock.lock();
                for (char c : c1) {
                    System.out.println(c);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            try {
                lock.lock();
                for (char c : c2) {
                    condition1.signal();
                    System.out.println(c);
                    condition2.await();
                }
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }
}
