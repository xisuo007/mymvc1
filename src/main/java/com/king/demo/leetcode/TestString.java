package com.king.demo.leetcode;

/**
 * Created by ljq on 2019/1/24 17:54
 */
public class TestString {


    public void reverseString(char[] s) {
        int mid = s.length/2;
        int length = s.length-1;
        for (int i = 0; i < mid; i++) {
            char temp = s[i];
            s[i] = s[length-i];
            s[length-i] = temp;
        }
    }

    public static void main(String[] args){
        int j = (int)Math.sqrt(13);
        System.out.println(j);
    }
}
