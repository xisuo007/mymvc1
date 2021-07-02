package com.king.lru;

import com.king.collection.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ljq on 2019/6/21 9:58
 * 缓存淘汰策略
 */
public class TestLru<K,V> extends LinkedHashMap<K,V> {

    //这里就是传递进来最多能缓存多少数据
    private final int CACHE_SIZE;

    public TestLru(int cacheSize){
        //这块就是设置一个hashmap的初始大小，同时最后一个true指的是让linkhashmap按照访问顺序来进行排序，最近访问的放在头，最老访问的就在尾
        super((int)Math.ceil(cacheSize/0.75)+1,0.75f,true);
        //super(3,0.75f,true);
        CACHE_SIZE = cacheSize;
    }

    //如果map中的数据量大于指定的缓存个数时2，就自动删除掉最老的数据
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>CACHE_SIZE;
    }

    public static void main(String[] args){

        TestLru<Integer,String> cache = new TestLru<>(4);
        System.out.println(cache.CACHE_SIZE);
        cache.put(1,"a");
        cache.put(2,"b");
        cache.put(3,"c");
        cache.get(2);
        cache.put(4,"d");
        cache.put(5,"e");
        cache.get(2);
        cache.put(6,"f");
        cache.put(7,"g");
        cache.get(2);
        cache.put(8,"m");
        cache.put(9,"n");
        System.out.println(cache.keySet());

    }
}
