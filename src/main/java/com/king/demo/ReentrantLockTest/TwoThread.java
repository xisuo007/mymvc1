package com.king.demo.ReentrantLockTest;

import com.sun.org.apache.regexp.internal.REUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljq on 2019/3/20 13:19
 */
public class TwoThread {
    private int start = 1;
    private volatile boolean flag = false;
    private final static Lock LOCK = new ReentrantLock();
    public static void main(String[] args){
        TwoThread test = new TwoThread();
        OuNum ou = new OuNum(test);
        JiNum ji = new JiNum(test);
        Thread t1 = new Thread(ou,"偶数");
        Thread t2 = new Thread(ji,"奇数");
        t1.start();
        t2.start();


    }
    public static class OuNum implements Runnable{
        private TwoThread number;

        public OuNum(TwoThread number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start<1000){
                if (number.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName()+"++"+number.start);
                        number.start++;
                        number.flag= false;
                    }finally {
                        LOCK.unlock();
                    }
                }

            }
        }
    }
    public static class JiNum implements Runnable{
        private TwoThread number;

        public JiNum(TwoThread number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start<1000){
                if (!number.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName()+"++"+number.start);
                        number.start++;
                        number.flag= true;
                    }finally {
                        LOCK.unlock();
                    }
                }

            }
        }
    }

}


