package com.king.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 5种方法来启动一个线程
 * Created by ljq on 2020/1/13 16:54
 */
public class StartThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();

        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();

        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> task = new FutureTask<>(myCallable);
        new Thread(task).start();
        System.out.println(task.get());
    }

    static class MyThread extends Thread{
        public void run() {
            System.out.println("继承自Thread");
        }
    }

    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println("runnable接口实现");
        }
    }

    static class MyCallable implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            return 123;
        }
    }


}
