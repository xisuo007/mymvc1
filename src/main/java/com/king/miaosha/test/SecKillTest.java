package com.king.miaosha.test;

import com.king.miaosha.RedisClient;
import com.king.miaosha.interceptor.CacheLockInterceptor;
import com.king.miaosha.service.TestKill;
import com.king.miaosha.service.impl.TestKillImpl;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;


/**
 * Created by ljq on 2019/6/25 14:45
 */
public class SecKillTest {
    private static Long commidityId1 = 10000001L;
    private static Long commidityId2 = 10000002L;
    private RedisClient client;
    public static String HOST = "192.168.4.201";
    private JedisPool jedisPool = new JedisPool(HOST);

    @Test
    public void testSecKill(){
        int threadCount = 1000;
        int splitPoint = 500;
        CountDownLatch endCount = new CountDownLatch(threadCount);
        CountDownLatch beginCount = new CountDownLatch(1);
        TestKillImpl testKill = new TestKillImpl();

        Thread[] threads = new Thread[threadCount];
        //起500个线程，秒杀第一个商品
        for (int i = 0; i < splitPoint; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        //等待在一个信号量上，挂起
                        beginCount.await();
                        //用动态代理的方式调用secKill方法
                        TestKill proxy = (TestKill)Proxy.newProxyInstance(TestKill.class.getClassLoader(),new Class[]{TestKill.class}, (InvocationHandler) new CacheLockInterceptor(testKill));
                        proxy.secKill("test",commidityId1);
                        endCount.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < splitPoint; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        //等待在一个信号量上，挂起
                        beginCount.await();
                        //用动态代理的方式调用secKill方法
                        TestKill proxy = (TestKill)Proxy.newProxyInstance(TestKill.class.getClassLoader(),new Class[]{TestKill.class}, (InvocationHandler) new CacheLockInterceptor(testKill));
                        proxy.secKill("test",commidityId2);
                        endCount.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }

        long startTime = System.currentTimeMillis();
        //主线程释放开始信号量，并等待结束信号量
        beginCount.countDown();


        try {
            //主线程等待结束信号量
            endCount.await();
            //观察秒杀结果是否正确
            System.out.println(TestKillImpl.inventory.get(commidityId1));
            System.out.println(TestKillImpl.inventory.get(commidityId2));
            System.out.println("error count"+CacheLockInterceptor.ERROR_COUNT);
            System.out.println("total cost"+(System.currentTimeMillis() - startTime));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
