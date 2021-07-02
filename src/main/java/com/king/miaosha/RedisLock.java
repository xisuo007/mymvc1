package com.king.miaosha;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by ljq on 2019/6/24 16:42
 */
public class RedisLock {

    @Autowired
    private RedisClient redisClient;

    //纳秒和毫秒之间的转换率
    public static final long MILLI_NANO_TIME = 1000 * 1000L;

    public static final String LOCKED = "TRUE";

    public static final Random RANDOM = new Random();

    private String key;
    private boolean lock = true;

    public RedisLock(String purpose, String key){
        this.key = purpose + "_" + key + "_lock";
    }

    /**
     * 加锁
     * 使用方式为：lock();
     * try{executeMethod();}finally{unlock();}
     * @param timeout  timeout的时间范围内轮询锁
     * @param expire   设置锁超时时间
     * @return
     */
    public boolean lock(long timeout, int expire){
        long nanoTime = System.nanoTime();
        timeout *= MILLI_NANO_TIME;

        try {
            //在timeout的时间范围内不断轮询锁
            while (System.nanoTime() - nanoTime < timeout){
                //锁不存在的话，设置锁并设置锁过期时间，即加锁
                if (redisClient.setnx(this.key,LOCKED) == 1) {
                    redisClient.expire(key,expire);//设置锁过期时间是为了在没有释放锁的情况下锁过期后消失，不会造成永久阻塞
                    this.lock = true;
                    return this.lock;
                }
                System.out.println("出现锁等待");
                //短暂休眠，避免可能的活锁
                Thread.sleep(3,RANDOM.nextInt(30));
            }
        } catch (Exception e) {
            throw new RuntimeException("locking error",e);
        }
        return false;
    }

    public void unlock(){

        try {
            if (this.lock) {
                redisClient.delKey(key);//直接删除
            }
        } catch (Throwable e) {

        }
    }
}
