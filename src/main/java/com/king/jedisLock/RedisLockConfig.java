package com.king.jedisLock;

import com.crossoverjie.distributed.lock.redis.RedisLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * Created by ljq on 2019/3/15 14:15
 */
@Configuration
public class RedisLockConfig {

    @Bean
    public RedisLock build(){
        RedisLock redisLock = new RedisLock();
        Jedis jedis = new Jedis("127.0.0.1",6379);//填写实际的redis地址和端口
        redisLock.setJedis(jedis);
        return redisLock;
    }
}
