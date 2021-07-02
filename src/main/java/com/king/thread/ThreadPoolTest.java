package com.king.thread;

import java.util.concurrent.*;

/**
 * Created by ljq on 2019/9/10 17:23
 * @author
 */
public class ThreadPoolTest {

    public static void main(String[] args){
        BlockingQueue blockingQueue = new ArrayBlockingQueue(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, blockingQueue);
        for (int i = 0; i < 20; i++) {
            Runnable runnable = new TaskBusyWithoutResult();
        }
        pool.shutdown();

        //可以直接用Executors创建出不同的线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//一个任务创建一个线程
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);//所有任务只能使用固定大小的线程；
        ExecutorService executorService = Executors.newSingleThreadExecutor();//相当于大小为1的fixedThreadPool
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);//带定时任务的线程池
        for (int i = 0; i < 5; i++) {
            System.out.println("Executors线程池运行");
            cachedThreadPool.execute(new TaskBusyWithoutResult());
        }
        cachedThreadPool.shutdown();
        Future<String> submit = fixedThreadPool.submit(() -> {
            System.out.println("测试返回参数");
            return "222";
        });
        try {
            String s = submit.get();
            System.out.println(s);
            fixedThreadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    static class TaskBusyWithoutResult implements Runnable{
        public TaskBusyWithoutResult(){}
        @Override
        public void run() {
            System.out.println("线程:"+Thread.currentThread()+"开始运行");
            int i = 10000*10000;
            while (i > 0) {
                i--;
            }
            System.out.println("线程:"+Thread.currentThread()+"运行结束");
        }
    }


}
