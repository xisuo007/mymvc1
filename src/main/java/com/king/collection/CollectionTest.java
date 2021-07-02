package com.king.collection;

import com.king.collection.resource.IResourceMap;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ljq on 2020/3/5 10:04
 */
public class CollectionTest {

    @Resource
    Map<String, IResourceMap> map;

    /**
     * collection  中包含 list set queue接口
     *      list--ArrayList/LinkedList/Vector
     *      queue--PriorityQueue/LinkedList
     *      set--HashSet/LinkedHashSet/SortedSet（TreeSet）
     */
    public static void main(String[] args){
        //Arrays.toList()用到的是适配器模式，只能用包装类型做为参数Integer[]   底层是一个固定大小的list，是它自己的，不能修改/添加
        Integer[] arr = {1,2,3,4};
        List<Integer> list = Arrays.asList(arr);
        System.out.println(list.toString());
        int[] arr1 = {4,5,6};
        List<int[]> ints = Arrays.asList(arr1);
        System.out.println(ints.toString());

        //Vector和ArrayList一个线程安全一个线程不安全，替代方案可以用Collections.synchronizedList(); 得到一个线程安全的 ArrayList
        ArrayList<Object> list1 = new ArrayList<>();
        List<Object> synList = Collections.synchronizedList(list1);
        //也可以用juc里的concurrent并发包下的CopyOnWriteArrayList 类
        CopyOnWriteArrayList<Object> copy = new CopyOnWriteArrayList<>();


    }

    /**
     * CopyOnWriteArrayList
     * 写操作在一个复制出来的数组上进行，同时进行加锁操作，防止并发写入导致数据丢失
     * 读操作在原始的数组上进行
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }

    final void setArray(Object[] a) {
        array = a;
    }
    @SuppressWarnings("unchecked")
    private E get(Object[] a, int index) {
        return (E) a[index];
    }
    */
}
