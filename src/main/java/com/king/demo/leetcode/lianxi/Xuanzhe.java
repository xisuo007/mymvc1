package com.king.demo.leetcode.lianxi;

/**
 * Created by ljq on 2020/1/19 11:30
 */
public class Xuanzhe {
    public static void main(String[] args){
        int[] arr = {4,7,5,3,8,2,9,1,6};
        for (int i = 0; i < arr.length-1; i++) {
            int min = i;
            for (int j = i+1; j < arr.length; j++) {
                min = arr[min]>arr[j]?j:min;
            }
            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
