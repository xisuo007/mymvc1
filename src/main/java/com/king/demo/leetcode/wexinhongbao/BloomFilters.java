package com.king.demo.leetcode.wexinhongbao;


/**
 * Created by ljq on 2019/3/20 13:42
 */
public class BloomFilters {

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        BloomFilters test = new BloomFilters(10000000);
        for (int i = 0; i < 10000000; i++) {
            test.add(i+"");
        }
        System.out.println(test.check(1+""));
        System.out.println(test.check(3+""));
        System.out.println(test.check(5+""));
        System.out.println(test.check(999999+""));
        System.out.println(test.check(400230340+""));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }
    //数组长度
    private int arraySize;
    //数组
    private int[] array;

    public BloomFilters(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    //写入数据
    public void add(String key){
        int first = hash1(key);
        int second = hash2(key);
        int third = hash3(key);
        array[first%arraySize] = 1;
        array[second%arraySize] = 1;
        array[third%arraySize] = 1;
    }

    //判断数据是否存在
    public boolean check(String key){
        int first = hash1(key);
        int second = hash2(key);
        int third = hash3(key);
        if (array[first%arraySize] == 0) {
            return false;
        }
        if (array[second%arraySize] == 0) {
            return false;
        }
        if (array[third%arraySize] == 0) {
            return false;
        }
      return true;
    }

    //hash1
    public int hash1(String key ){
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) {
            hash = 33*hash + key.charAt(i);
        }
        return Math.abs(hash);
    }
    //hash2
    public int hash2(String key ){
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash^key.charAt(i))*p;
        }
        hash+=hash<<13;
        hash^=hash>>7;
        hash+=hash<<3;
        hash^=hash>>17;
        hash+=hash<<5;
        return  Math.abs(hash);
    }
    //hash3
    public int hash3(String key ){
        int hash,i;
        for (hash = 0,i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash+=(hash<<10);
            hash^=(hash>>6);
        }
        hash+=(hash<<3);
        hash^=(hash>>11);
        hash+=(hash<<15);
        return  Math.abs(hash);
    }
}
