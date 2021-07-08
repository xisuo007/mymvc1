package com.king.thread.taskfuture;

import java.util.concurrent.*;

/**
 * future代表一个任务，提交过之后会异步执行，不影响后面操作
 * future中的5个方法：
 * 1.boolean cancel (boolean mayInterruptIfRunning) 取消任务的执行。参数指定是否立即中断任务执行，或者等等任务结束
 * 2.boolean isCancelled () 任务是否已经取消，任务正常完成前将其取消，则返回 true
 * 3.boolean isDone () 任务是否已经完成。需要注意的是如果任务正常终止、异常或取消，都将返回true
 * 4.V get () throws InterruptedException, ExecutionException 等待任务执行结束，然后获得V类型的结果。
 *         InterruptedException 线程被中断异常， ExecutionException任务执行异常，如果任务被取消，还会抛出CancellationException
 * 5.V get (long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
 *         同上面的get功能一样，多了设置超时时间。参数timeout指定超时时间，uint指定时间的单位，在枚举类TimeUnit中有相关的定义。
 *         如果计 算超时，将抛出TimeoutException
 *
 * 缺点-------->future没有提供通知的机制，不知道具体方法执行完了没有，除非调用.get()方法阻塞。或者调用isDone()轮询查状态，这样会消耗cpu资源
 *
 * Created by ljq on 2021-7-6 19:09
 */
public class TestFutureTask {
    ExecutorService executor = Executors.newCachedThreadPool();
    public static void main(String[] args){
        TestFutureTask task = new TestFutureTask();
        Future<String> futureData = task.getFutureData();

        //1.通过get方法阻塞等待获取返回结果
        try {
            System.out.println(futureData.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //1.1 通过get(500,)方法阻塞等待获取返回结果,超时会抛异常
        //try {
        //    try {
        //        System.out.println(futureData.get(500, TimeUnit.MILLISECONDS));
        //    } catch (TimeoutException e) {
        //        System.out.println("执行future方法超时");
        //        e.printStackTrace();
        //    }
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //} catch (ExecutionException e) {
        //    e.printStackTrace();
        //}

        //2.轮询查询状态获取返回结果
        while (futureData.isDone()) {
            try {
                System.out.println(futureData.get());
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private Future<String> getFutureData(){

        Future<String> future = executor.submit(() -> {
            System.out.println("执行ruannble中的方法！");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future";
        });
        System.out.println("正常执行逻辑！");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
        return future;
    }
}
