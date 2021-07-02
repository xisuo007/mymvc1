package com.king.jvm;

/**
 * Created by ljq on 2020-11-17 13:38
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
 * -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc2.log
 * 
 * new区5M内存  新生代最大内存5M  堆初始大小10M  堆最大为10M  new区中Eden占比80%  最大年龄15  大对象的大小  parNew+cms
 */
public class Demo2 {

    public static void main(String[] args) {
        byte[] array1 = new byte[2 * 1024 * 1024];
        array1 = new byte[2 * 1024 * 1024];
        array1 = new byte[2 * 1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[128 * 1024];

        byte[] array3 = new byte[2 * 1024 * 1024];
        //array3 = new byte[2 * 1024 * 1024];
        //array3 = new byte[128 * 1024];
        //array3 = null;
        //
        //byte[] array4 = new byte[2 * 1024 * 1024];


    }
}
