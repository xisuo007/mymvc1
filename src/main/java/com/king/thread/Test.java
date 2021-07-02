package com.king.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by ljq on 2020/3/6 15:01
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //ForkJoinExample example = new ForkJoinExample(1,2000000000);
        //ForkJoinPool pool = new ForkJoinPool();
        //long start = System.currentTimeMillis();
        //Future result = pool.submit(example);
        //System.out.println(result.get());
        //long end = System.currentTimeMillis();
        //System.out.println("耗时："+(end - start));


        int ret = 0;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 2000000000; i++) {
            ret += i;
        }
        long end1 = System.currentTimeMillis();
        System.out.println("耗时："+(end1 - start1));

    }
}
