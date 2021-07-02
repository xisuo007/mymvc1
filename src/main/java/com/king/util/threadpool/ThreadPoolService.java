package com.king.util.threadpool;

import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池
 **/
@Service("threadPoolService")
public class ThreadPoolService {

    private final static int    processorsNum = Runtime.getRuntime().availableProcessors() + 10;

    // 构造线程池，异步处理
    ThreadPoolExecutor          threadPool    = new ThreadPoolExecutor(processorsNum, processorsNum, 300,
                                                                       TimeUnit.SECONDS,
                                                                       new LinkedBlockingQueue<Runnable>(300),
                                                                       new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 调用线程池执行任务
     * 
     * @param command
     */
    public void execute(Runnable command) {
        threadPool.execute(command);
    }

}
