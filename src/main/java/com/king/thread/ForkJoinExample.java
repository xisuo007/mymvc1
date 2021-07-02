package com.king.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by ljq on 2020/3/6 14:50
 */
public class ForkJoinExample extends RecursiveTask<Integer> {

    private final int threshold = 1000;
    private int first;
    private int last;

    public ForkJoinExample(int first,int last){
        this.first = first;
        this.last = last;
    }


    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            //范围小的话直接计算
            for (int i = first; i < last; i++) {
                result += i;
            }
        }else {
            int middle = first + (last - first)/2;
            ForkJoinExample left = new ForkJoinExample(first, middle);
            ForkJoinExample right = new ForkJoinExample(middle+1, last);
            left.fork();
            right.fork();
            result = left.join() + right.join();
        }
        return result;
    }
}
