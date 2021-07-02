package com.king.util.redis;


import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis工具类
 **/
public class RedisUtils {

    RedisTemplate<String, Object> redisTemplate;
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 切换redis basebase
     * @param dbIndex basebase编号 0-15
     */
    private void setIndex( Integer dbIndex){
        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(dbIndex);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //jedisConnectionFactory.resetConnection();
    }
    /**
     opsForValue redis 操作 即 String存储时进行处理
     */


    /**
     *
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expireToString( String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpireToString( String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }


    /**
     *  判断key是否存在
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKeyToString( Integer dbIndex, String key){
        try {
            this.setIndex(dbIndex);
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean setToString( Integer dbIndex,String key,Object value) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置变量值的过期时间
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value 值
     * @param time 有效是假
     * @return true成功 false失败
     */
    public boolean setToString( Integer dbIndex,String key,Object value,long time) {
        try {
            this.setIndex(dbIndex);
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存获取
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @return
     */
    public Object getToString( Integer dbIndex, String key){
        this.setIndex(dbIndex);
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 在原有的值基础上新增字符串到末尾
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param value 值
     * @return
     */
    public Integer appendToString( Integer dbIndex,String key,String value) {
            this.setIndex(dbIndex);
            return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 截取key键对应值得字符串，从开始下标位置开始到结束下标的位置(包含结束下标)的字符串
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param start 开始位置
     * @param end  介绍位置
     * @return
     */
    public Object getSubToString( Integer dbIndex, String key, long start, long end){
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().get("stringValue",start,end);
    }

    /**
     * 获取原来key键对应的值并重新赋新值
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param value 值
     * @return
     */
    public Object getAndSetToString( Integer dbIndex,String key,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param offset 位置
     * @param value 设置值 true:1  false:0
     * @return
     */
    public boolean setBitToString( Integer dbIndex,String key, long offset, boolean value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().setBit(key,offset, value);
    }

    /**
     * 判断指定的位置ASCII码的bit位是否为1
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param offset 位置
     * @return true /false
     */
    public boolean getBitToString( Integer dbIndex,String key, long offset) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().getBit(key,offset);
    }

    /**
     * 获取指定字符串的长度
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @return 字符串长度
     */
    public Long sizeToString( Integer dbIndex,String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 以增量的方式将long值存储在变量中
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param delta 增长值
     * @return 增加后的值
     */
    public Long incrementToString( Integer dbIndex,String key, long delta) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 以增量的方式将double值存储在变量中
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param delta 增长值
     * @return 增加后的值
     */
    public double  incrementToString( Integer dbIndex,String key, double  delta) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 如果键不存在则新增,存在则不改变已经有的值
     * @param dbIndex basebase编号 0-15
     * @param key  键
     * @param value 值
     * @return
     */
    public boolean  setIfAbsentToString( Integer dbIndex,String key, Object  value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    /**
     * 删除String类型的指定key
     * @param dbIndex
     * @param key
     * @return
     */
    public void deleteToString(Integer dbIndex,String key) {
        this.setIndex(dbIndex);
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 设置map集合到redis
     * @param dbIndex basebase编号 0-15
     * @param map 数据对象
     * @return
     */
    public boolean  multiSetToString( Integer dbIndex, Map<String ,Object> map) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForValue().multiSet(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  根据集合取出对应的value值
     * @param dbIndex basebase编号 0-15
     * @param keys key队列
     * @return
     */
    public List<Object> multiSetToString( Integer dbIndex,Collection<String> keys) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 如果对应的map集合名称不存在，则添加，如果存在则不做修改
     * @param dbIndex basebase编号 0-15
     * @param map 数据对象
     * @return
     */
    public boolean  multiSetIfAbsentToString( Integer dbIndex, Map<String ,Object> map) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForValue().multiSetIfAbsent(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     opsForList redis 操作 即 List存储时进行处理
     */

    /**
     * 在变量左边添加元素值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value 值
     * @return 返回总长度
     */
    public Long leftPushToList( Integer dbIndex,String key,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param index 位置
     * @return
     */
    public Object indexToList( Integer dbIndex,String key,Long index) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定区间的值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param start 起始位置
     * @param end 结束位置 如果为负数增则到最后一个
     * @return
     */
    public List<Object> rangeToList( Integer dbIndex, String key,Long start,long end) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().range(key, start,end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param pivot 中间参数
     * @param value 放置的值
     * @return
     */
    public Long leftPushToList( Integer dbIndex, String key,Object pivot,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPush(key, pivot,value);
    }

    /**
     * 向左边批量添加参数元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 数据内容数组
     * @return
     */
    public Long leftPushAllToList( Integer dbIndex, String key,Object ... values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPushAll(key,values);
    }

    /**
     *以集合的方式向左边批量添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 集合
     * @return
     */
    public Long leftPushAllToList( Integer dbIndex, String key,Collection<Object> values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPushAll(key,values);
    }

    /**
     * 如果存在集合则添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value
     * @return
     */
    public Long leftPushIfPresentToList( Integer dbIndex, String key,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPushIfPresent(key,value);
    }



    /**
     * 向集合最右边添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value 值
     * @return 返回总长度
     */
    public Long rightPushToList( Integer dbIndex, String key,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param pivot 中间参数
     * @param value 放置的值
     * @return
     */
    public Long rightPushToList( Integer dbIndex, String key,Object pivot,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPush(key, pivot,value);
    }


    /**
     * 向右边批量添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 数据内容数组
     * @return
     */
    public Long rightPushAllToList( Integer dbIndex, String key,Object ... values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPushAll(key,values);
    }

    /**
     * 以集合方式向右边添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 集合
     * @return
     */
    public Long rightPushAllToList( Integer dbIndex, String key,Collection<Object> values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPushAll(key,values);
    }

    /**
     *  向已存在的集合中添加元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param value
     * @return
     */
    public Long rightPushIfPresentToList( Integer dbIndex, String key,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPushIfPresent(key,value);
    }

    /**
     * 获取集合长度
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Long sizeToList( Integer dbIndex, String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 移除集合中的左边第一个元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Object leftPopToList( Integer dbIndex, String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param timeout 等待时间
     * @return
     */
    public Object leftPopToList( Integer dbIndex, String key,long timeout) {
        this.setIndex(dbIndex);
        if(timeout>0){
            return redisTemplate.opsForList().leftPop(key,timeout,TimeUnit.SECONDS);
        }else{
            return redisTemplate.opsForList().leftPop(key);
        }
    }

    /**
     * 移除集合中右边的元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Object rightPopToList( Integer dbIndex, String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param timeout 等待时间
     * @return
     */
    public Object rightPopToList( Integer dbIndex, String key,long timeout) {
        this.setIndex(dbIndex);
        if(timeout>0){
            return redisTemplate.opsForList().rightPop(key,timeout,TimeUnit.SECONDS);
        }else{
            return redisTemplate.opsForList().rightPop(key);
        }
    }

    /**
     * 移除集合中右边的元素，同时在左边加入一个元素
     * @param dbIndex  basebase编号 0-15
     * @param sourceKey 加入元素的key
     * @param destinationKey 删除元素的key
     * @return
     */
    public Object rightPopAndLeftPushToList( Integer dbIndex, String sourceKey,String destinationKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,destinationKey);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出
     * @param dbIndex  basebase编号 0-15
     * @param sourceKey 加入元素的key
     * @param destinationKey 删除元素的key
     * @param timeout 等待时间（S）
     * @return
     */
    public Object rightPopAndLeftPushToList( Integer dbIndex, String sourceKey,String destinationKey,long timeout) {
        this.setIndex(dbIndex);
        if(timeout>0){
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,destinationKey,timeout,TimeUnit.SECONDS);
        }else{
            return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,destinationKey);
        }
    }

    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param index 插入位置
     * @param value 内容
     * @return 操作是否成功
     */
    public boolean setToList( Integer dbIndex, String key,long index,Object value) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForList().set(key,index,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param count count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
     * @param value 需要删除内容
     * @return 返回删除数量
     */
    public Long removeToList( Integer dbIndex, String key,long count,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForList().remove(key,count,value);
    }

    /**
     * 截取集合元素长度，保留长度内的数据
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public boolean trimToList( Integer dbIndex, String key, long start, long end) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForList().trim(key,start,start);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     opsForHash redis 操作 即 HashMap存储时进行处理
     */
    /**
     * 新增hashMap值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @param value 值
     * @return
     */
    public boolean putToHash( Integer dbIndex, String key, String hashKey, Object value) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForHash().put(key,hashKey,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定变量中的hashMap值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public List<Object> valuesToHash( Integer dbIndex, String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取变量中的键值对
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Map<Object,Object> entriesToHash( Integer dbIndex, String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @return
     */
    public Object getToHash( Integer dbIndex, String key, String hashKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * 判断变量中是否有指定的map键
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @return
     */
    public boolean hasKeyToHash( Integer dbIndex, String key, String hashKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    /**
     * 获取变量中的键
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Set<Object> keysToHash( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     *  获取变量的长度
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public long sizeToHash( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 使变量中的键以double值的大小进行自增长
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @param delta 增长大小
     * @return
     */
    public Double incrementToHash( Integer dbIndex,  String key,String hashKey,double delta) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().increment(key,hashKey,delta);
    }

    /**
     * 使变量中的键以double值的大小进行自增长
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @param delta 增长大小
     * @return
     */
    public long incrementToHash( Integer dbIndex,  String key,String hashKey,long delta) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().increment(key,hashKey,delta);
    }

    /**
     * 以集合的方式获取变量中的值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKeys hash中key 集合
     * @return
     */
    public List<Object>  multiGetToHash( Integer dbIndex,  String key,Collection<Object> hashKeys) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().multiGet(key,hashKeys);
    }

    /**
     * 以map集合的形式添加键值对
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param map 需要添加的map集合
     * @return
     */
    public boolean  putAllToHash( Integer dbIndex,  String key,Map<Object,Object> map) {
        try {
            this.setIndex(dbIndex);
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 如果变量值存在，在变量中可以添加不存在的的键值对，如果变量不存在，则新增一个变量，同时将键值对添加到该变量
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKey hash中key
     * @param value 值
     * @return
     */
    public Boolean  putIfAbsentToHash( Integer dbIndex,  String key,String hashKey,Object value) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().putIfAbsent(key,hashKey,value);
    }

    /**
     * 删除变量中的键值对，可以传入多个参数，删除多个键值对
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param hashKeys hash中key 集合
     * @return
     */
    public Long  deleteToHash( Integer dbIndex,  String key,String... hashKeys) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForHash().delete(key,hashKeys);
    }

    /**
     opsForValue redis 操作 即 String存储时进行处理
     */
    /**
     * 向变量中批量添加值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 值
     * @return
     */
    public Long  addToSet( Integer dbIndex,  String key,String... values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().add(key,values);
    }

    /**
     * 获取变量中的值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Set  membersToSet( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取变量中值的长度
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public long  sizeToSet( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 随机获取变量中的元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Object  randomMemberToSet( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param count 数量
     * @return
     */
    public List<Object>  randomMembersToSet( Integer dbIndex,  String key,long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().randomMembers(key,count);
    }

    /**
     * 检查给定的元素是否在变量中
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param object 元素
     * @return
     */
    public boolean  isMemberToSet( Integer dbIndex,  String key,Object object) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().isMember(key,object);
    }

    /**
     * 转移变量的元素值到目的变量
     * @param dbIndex basebase编号 0-15
     * @param key 转移变量key
     * @param value 元素值
     * @param destKey 目的变量
     * @return
     */
    public boolean  moveToSet( Integer dbIndex,  String key,Object value,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().move(key,value,destKey);
    }

    /**
     * 弹出变量中的元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @return
     */
    public Object  popToSet( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     *批量移除变量中的元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param values 元素列
     * @return
     */
    public Long  removeToSet( Integer dbIndex,  String key,Object ... values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().remove(key,values);
    }

    /**
     *  匹配获取键值对，ScanOptions.NONE为获取全部键值对；ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param options 匹配条件
     * @return
     */
    public Cursor<Object> scanToSet(Integer dbIndex, String key, ScanOptions options) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().scan(key,options);
    }

    /**
     * 通过集合求差值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKeys
     * @return
     */
    public Set<Object>  differenceToSet( Integer dbIndex,  String key,  Collection<String> otherKeys) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().difference(key,otherKeys);
    }

    /**
     * 通过给定的key求2个set变量的差值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @return
     */
    public Set<Object>  differenceToSet( Integer dbIndex,  String key,  String otherKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().difference(key,otherKey);
    }

    /**
     *  将求出来的差值元素保存
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @param destKey 差异存储key
     * @return
     */
    public Long  differenceAndStoreToSet( Integer dbIndex,  String key,  String otherKey,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().differenceAndStore(key,otherKey,destKey);
    }

    /**
     * 将求出来的差值元素保存
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKeys 比对key集合
     * @param destKey 差异存储key
     * @return
     */
    public Long  differenceAndStoreToSet( Integer dbIndex,  String key,  Collection<String> otherKeys,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().differenceAndStore(key,otherKeys,destKey);
    }

    /**
     * 获取去重的随机元素
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param count 数量
     * @return
     */
    public Set distinctRandomMembersToSet( Integer dbIndex,  String key, long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().distinctRandomMembers(key,count);
    }

    /**
     * 获取2个变量中的交集
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @return
     */
    public Set intersectToSet( Integer dbIndex,  String key, String otherKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().intersect(key,otherKey);
    }

    /**
     * 获取2个变量交集后保存到最后一个参数上
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @param destKey 结果存储key
     * @return
     */
    public Long intersectAndStoreToSet( Integer dbIndex,  String key, String otherKey,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().intersectAndStore(key,otherKey,destKey);
    }

    /**
     * 获取多个变量的交集并保存到最后一个参数上
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKeys 比对key集合
     * @param destKey 结果存储key
     * @return
     */
    public Long intersectAndStoreToSet( Integer dbIndex,  String key,  Collection<String> otherKeys,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().intersectAndStore(key,otherKeys,destKey);
    }

    /**
     * 获取2个变量的合集
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @return
     */
    public Set unionToSet( Integer dbIndex,  String key,  String otherKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().union(key,otherKey);
    }

    /**
     *  获取多个变量的合集
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKeys 比对key集合
     * @return
     */
    public Set unionToSet( Integer dbIndex,  String key,Collection<String> otherKeys) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().union(key,otherKeys);
    }

    /**
     * 获取2个变量合集后保存到最后一个参数上
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKey 比对key
     * @param destKey 结果存储key
     * @return
     */
    public Long unionAndStoreToSet( Integer dbIndex,  String key,  String otherKey,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().unionAndStore(key,otherKey,destKey);
    }

    /**
     * 获取多个变量的合集并保存到最后一个参数上
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param otherKeys 比对key集合
     * @param destKey 结果存储key
     * @return
     */
    public Long unionAndStoreToSet( Integer dbIndex,  String key,   Collection<String> otherKeys,String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForSet().unionAndStore(key,otherKeys,destKey);
    }

    /**
     opsForZSet redis 操作 即 zset存储时进行处理
     */

    public boolean addToZSet( Integer dbIndex,  String key,  Object value,double score) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * 添加元素到变量中同时指定元素的分值
     * @param dbIndex  basebase编号 0-15
     * @param key 键
     * @param start 变量值区间范围
     * @param end 变量值区间范围  如果是-1 表示无限制
     *
     * @return
     */
    public Set<Object>  rangeToZSet( Integer dbIndex,  String key,  long start, long end) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    /**
     * 用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param range 规则
     * @return
     */
    public Set<Object>  rangeByLexToZSet( Integer dbIndex,  String key, RedisZSetCommands.Range range) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByLex(key,range);
    }

    /**
     * 用于获取满足非score的设置下标开始的长度排序取值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param range 规则
     * @param limit 范围
     * @return
     */
    public Set<Object>  rangeByLexToZSet( Integer dbIndex,  String key, RedisZSetCommands.Range range,RedisZSetCommands.Limit limit) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByLex(key,range,limit);
    }

    /**
     * 通过TypedTuple方式新增数据
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param tuples 添加数组
     * @return
     */
    public Long  addToZSet( Integer dbIndex,  String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().add(key,tuples);
    }

    /**
     * 根据设置的score获取区间值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min score 最小范围
     * @param max score 最大范围
     * @return
     */
    public Set<Object>  rangeByScoreToZSet( Integer dbIndex,  String key, double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByScore(key,min,max);
    }

    /**
     * 根据设置的score获取区间值从给定下标和给定长度获取最终值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min score 最小范围
     * @param max score 最大范围
     * @param offset 起始位置
     * @param count 长度
     * @return
     */
    public Set<Object>  rangeByScoreToZSet( Integer dbIndex,  String key, double min, double max,long offset, long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByScore(key,min,max,offset,count);
    }

    /**
     * 获取RedisZSetCommands.Tuples的区间值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param start 根据score值排序后起始位置
     * @param end 根据score值排序后结束位置
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>>  rangeWithScoresToZSet( Integer dbIndex,  String key, long start, long end) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeWithScores(key,start,end);
    }

    /**
     * 获取RedisZSetCommands.Tuples的区间值通过分值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值开始
     * @param max 分值结束 -1 表示最大
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>>  rangeByScoreWithScoresToZSet( Integer dbIndex,  String key,double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max);
    }

    /**
     *  获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值开始
     * @param max 分值结束 -1 表示最大
     * @param offset 开始位置
     * @param count 获取数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>>  rangeByScoreWithScoresToZSet( Integer dbIndex,  String key,double min, double max, long offset, long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max,offset,count);
    }

    /**
     * 获取区间值的个数
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值开始
     * @param max 分值结束 -1 表示最大
     * @return
     */
    public long countToZSet( Integer dbIndex,  String key,double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().count(key,min,max);
    }

    /**
     * 获取变量中元素的索引,下标开始位置为0
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param object 变量元素
     * @return
     */
    public long rankToZSet( Integer dbIndex,  String key,Object object) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().rank(key,object);
    }

    /**
     * 匹配获取键值对，ScanOptions.NONE为获取全部键值对；ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param options 规则
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<Object>> scanToZSet( Integer dbIndex,  String key,ScanOptions options) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().scan(key,options);
    }

    /**
     * 获取元素的分值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param object  元素
     * @return
     */
    public double  scoreToZSet( Integer dbIndex,  String key,Object object) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().score(key,object);
    }

    /**
     * 获取变量中元素的个数
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @return
     */
    public long  zCardToZSet( Integer dbIndex,  String key) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 修改变量中的元素的分值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param value 元素
     * @param delta 增加分值
     * @return
     */
    public double incrementScoreToZSet( Integer dbIndex,  String key, Object value, double delta) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().incrementScore(key,value,delta);
    }

    /**
     * 倒序排列指定分值区间元素
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值最小值
     * @param max 分值最大值
     * @return
     */
    public Set reverseRangeByScoreToZSet( Integer dbIndex,  String key,double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max);
    }

    /**
     * 倒序排列从给定下标和给定长度分值区间元素
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值最小值
     * @param max 分值最大值
     * @param offset 起始位置
     * @param count 数量
     * @return
     */
    public Set reverseRangeByScoreToZSet( Integer dbIndex,  String key,double min, double max, long offset, long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max,offset,count);
    }

    /**
     *  倒序排序获取RedisZSetCommands.Tuples的分值区间值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值最小值
     * @param max 分值最大值
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScoresToZSet( Integer dbIndex,  String key,double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,min,max);
    }

    /**
     * 倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度分值区间值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值最小值
     * @param max 分值最大值
     * @param offset 起始位置
     * @param count 数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScoresToZSet( Integer dbIndex,  String key,double min, double max, long offset, long count) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,min,max,offset,count);
    }

    /**
     * 索引倒序排列区间值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param start 根据score值排序后起始位置
     * @param end 根据score值排序后结束位置
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScoresToZSet( Integer dbIndex,  String key,long start, long end) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRangeWithScores(key,start,end);
    }

    /**
     * 获取倒序排列的索引值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param object 元素
     * @return
     */
    public long reverseRankToZSet( Integer dbIndex,  String key,Object object) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().reverseRank(key,object);
    }

    /**
     * 获取2个变量的交集存放到第3个变量里面
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param otherKey 第二个key
     * @param destKey 交集存放key
     * @return
     */
    public long intersectAndStoreToZSet( Integer dbIndex,  String key,String otherKey, String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().intersectAndStore(key,otherKey,destKey);
    }

    /**
     * 获取多个变量的交集存放到第3个变量里面
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param otherKeys
     * @param destKey 交集存放key
     * @return
     */
    public long intersectAndStoreToZSet( Integer dbIndex,  String key,Collection<String> otherKeys, String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().intersectAndStore(key,otherKeys,destKey);
    }

    /**
     * 获取2个变量的合集存放到第3个变量里面
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param otherKey 第二个key
     * @param destKey 交集存放key
     * @return
     */
    public long unionAndStoreToZSet( Integer dbIndex,  String key,String otherKey, String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().unionAndStore(key,otherKey,destKey);
    }

    /**
     * 获取多个变量的合集存放到第3个变量里面
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param otherKeys
     * @param destKey 交集存放key
     * @return
     */
    public long unionAndStoreToZSet( Integer dbIndex,  String key,Collection<String> otherKeys, String destKey) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().unionAndStore(key,otherKeys,destKey);
    }

    /**
     *    批量移除元素根据元素值
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param values 删除元素
     * @return
     */
    public long removeToZSet( Integer dbIndex,  String key,Object ...values) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().remove(key,values);
    }

    /**
     * 根据分值移除区间元素
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param min 分值范围
     * @param max 分值范围
     * @return
     */
    public long removeRangeByScoreToZSet( Integer dbIndex,  String key, double min, double max) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
    }

    /**
     * 根据索引值移除区间元素
     * @param dbIndex basebase编号 0-15
     * @param key 键
     * @param start
     * @param end
     * @return
     */
    public long removeRangeToZSet( Integer dbIndex,  String key, long start, long end) {
        this.setIndex(dbIndex);
        return redisTemplate.opsForZSet().removeRange(key,start,end);
    }

}