package com.king.demo.ReentrantLockTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljq on 2019/3/12 14:55
 */
public class ReentrantLockTest {
    private ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args){

    }
    public void test(){
        lock.lock();
        //加三次锁
        for (int i = 0; i <= 3; i++) {
            lock.lock();
        }
        //再释放掉
        for (int i = 0; i <= 3; i++) {
            try {

            }finally {
                lock.unlock();
            }
        }
    }
}
