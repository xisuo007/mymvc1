package com.king.demo.leetcode.lianxi;

/**
 * Created by ljq on 2020/1/19 11:46
 */
public class MaoPao {
    public static void main(String[] args){
        int[] arr = {4,7,5,3,8,2,9,1,6};
        sort(arr);
        print(arr);
    }

    static void sort(int[] a){
        for (int i = a.length; i>=0;i--){
            for (int j = 0;j<i-1;j++){
                if (a[j] > a[j+1]) {
                    swap(a,j,j+1);
                }
            }
        }

    }
    static void swap(int[] a,int i,int j){
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    static void print(int[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
