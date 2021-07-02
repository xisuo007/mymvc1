package com.king.jedisLock;

import com.crossoverjie.distributed.lock.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by ljq on 2019/3/15 14:25
 */

public class TestJedis {
    //需要放入springboot环境测试，项目启动后，容器中管理RedisLock的bean之后
    @Autowired
    private RedisLock redisLock ;
    public void use() {
        String key = "key";
        String request = UUID.randomUUID().toString();
        try {
            boolean locktest = redisLock.tryLock(key, request);
            if (!locktest) {
                System.out.println("locked error");
                return;
            }
            //执行具体逻辑
        } finally {
            redisLock.unlock(key,request) ;
        }
    }
}
