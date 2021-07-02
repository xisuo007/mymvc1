package com.king.demo.ReentrantLockTest;

/**
 * Created by ljq on 2019/3/12 16:31
 */
public class TwoThreadWaitNotify {
    private int start = 1;
    private boolean flag = false;
    public static void main(String[] args){
        TwoThreadWaitNotify test = new TwoThreadWaitNotify();

        Thread t1 = new Thread(new OuNum(test));
        Thread t2 = new Thread(new JiNum(test));
        t1.setName("A");
        t2.setName("B");
        t1.start();
        t2.start();

    }

    public static class OuNum implements Runnable{
        private TwoThreadWaitNotify two;
        public OuNum(TwoThreadWaitNotify two){
            this.two = two;
        }
        @Override
        public void run() {
            while (two.start <= 100){
                synchronized (TwoThreadWaitNotify.class){

                    System.out.println("偶数线程抢到了锁");
                    if (two.flag) {
                        System.out.println(Thread.currentThread().getName()+"   偶数"+two.start);
                        two.start++;
                        two.flag = false;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class JiNum implements Runnable{
        private TwoThreadWaitNotify one;
        public JiNum(TwoThreadWaitNotify one){
            this.one = one;
        }
        @Override
        public void run() {
            while (one.start <= 100){
                synchronized (TwoThreadWaitNotify.class){

                    System.out.println("奇数线程抢到了锁");
                    if (!one.flag) {
                        System.out.println(Thread.currentThread().getName()+"   奇数"+one.start);
                        one.start++;
                        one.flag = true;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
