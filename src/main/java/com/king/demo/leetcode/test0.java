package com.king.demo.leetcode;


import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.lang.annotation.ElementType;
import java.util.*;

/**
 * Created by ljq on 2019/1/23 11:17
 */
public class test0 {
//一个数组中求某两个数的和等于某个数
    public int[] twoSum(int[] nums, int target) {
        int[] ret = new int[2];
        for (int i = 0; i < nums.length-1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    ret[0] = i;
                    ret[1] = j;
                }
            }
        }
        return ret;
    }

    /**
     * 用双指针方式，双指针方式主要对遍历数组，两个指针指向不同的元素，从而协同完成任务
     */
    public int[] twoSum2(int[] nums, int target){
        int i = 0;
        int j = nums.length - 1;
        while (i<j){
            int sum = nums[i] + nums[j];
            if(sum == target){
                return new int[]{nums[i],nums[j]};
            }else if(sum > target){
                j--;
            }else{
                i++;
            }
        }
        return null;
    }

    /**
     * 用双指针求一个数是不是某两个数的平方和
     */
    public boolean judgeSquareSum(int c){
        int i = 0,j = (int)Math.sqrt(c);
        while (i<j){
            int sum = i*i + j*j;
            if (sum == c) {
                return true;
            }else if (sum > c){
                j--;
            }else {
                i++;
            }
        }
        return false;
    }

    /**
     * 归并两个有序数组
     */
    public void merge(int[] a,int[] b,int n,int m){
        int i = n-1,j= m-1;
        int l = m+n-1;
        while (i>=0||j>=0){
            if (i<0){
                a[l--] = b[j--];
            }else if (j<0){
                a[l--] = a[i--];
            }else if (a[i]>b[j]){
                a[l--] = a[i--];
            }else {
                a[l--] = b[j--];
            }
        }
    }

    /**
     * 判断链表是否存在环
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode l1 = head, l2 = head.next;
        while (l1 != null && l2 != null && l2.next != null) {
            if (l1 == l2) {
                return true;
            }
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return false;
    }

    /**
     * 最长子序列
     * Input:
     s = "abpcplea", d = ["ale","apple","monkey","plea"]
     Output  :  "apple"
     题目描述：删除 s 中的一些字符，使得它构成字符串列表 d 中的一个字符串，找出能构成的最长字符串。如果有多个相同长度的结果，返回字典序的最小字符串。
     */
    public String findLongestWord(String s, List<String> d){
        String longestWord = "";
        for (String target : d) {
            int l1 = longestWord.length(),l2 = target.length();
            if (l1 > l2 || (l1 == l2 && longestWord.compareTo(target)<0)) {
                continue;
            }
            if(isValid(s,target)){
                longestWord = target;
            }
        }
        return longestWord;
    }

    /**
     * 出现频率最多的k个数
     * 例如[1,1,1,2,2,3]  k=2  return [1,2]
     */
    public List<Integer> topKFrequent(int[] nums, int k ){
        Map<Integer,Integer> fre = new HashMap<>();
        //把数组的值都放入map  key为具体值，value为个数
        for (int num : nums) {
            fre.put(num,fre.getOrDefault(num,0)+1);
        }
        //创建一个链表数组，每个数组里放一个链表   把以个数为下表的链表里放入具体的key
        List<Integer>[] ret = new ArrayList[nums.length+1];
        for (Integer key : fre.keySet()) {
            int value = fre.get(key);
            if (ret[value] == null) {
                ret[value] = new ArrayList<>();
            }
            ret[value].add(key);
        }
        List<Integer> topK = new ArrayList<>();
        for( int i = ret.length - 1; i >= 0 && topK.size() < k; i-- ){
            if (ret[i] == null) {
                continue;
            }
            if (ret[i].size() <= (k - topK.size())){
                topK.addAll(ret[i]);
            }else {
                topK.addAll(ret[i].subList(0,k-topK.size()));
            }

        }
        return topK;

    }

    private boolean isValid(String s,String target){
        int i = 0,j = 0;
        while (i<s.length() && j<target.length()){
            if (s.charAt(i) == target.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == target.length();
    }
//9宫格数独
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> set = new HashSet<>();
        List<HashSet<Character>> list = new ArrayList(9);
        List<HashSet<Character>> tlist = new ArrayList(9);
        List<Integer> nums = new ArrayList<>(9);
        List<Integer> tts = new ArrayList<>(9);
        for (int t = 0; t < 9; t++) {
            list.add(new HashSet<Character>());
            tlist.add(new HashSet<Character>());
            nums.add(0);
            tts.add(0);
        }
        char c = '.';
        int num = 0;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        int num7 = 0;
        int num8 = 0;
        int num9 = 0;
        for (int i = 0; i < board.length; i++) {
            set.clear();
            num = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != c) {
                    ++num;
                    set.add(board[i][j]);
                }
                if (board[j][i] != c) {
                    Integer integer = tts.get(i);
                    integer++;
                    tts.set(i,integer);
                    tlist.get(i).add(board[j][i]);
                }
                if (i < 3) {
                    if (j < 3) {
                        if (board[i][j] != c) {
                            num1++;
                            list.get(0).add(board[i][j]);
                        }
                    }
                    if (j >= 3 && j < 6) {
                        if (board[i][j] != c) {
                            num2++;
                            list.get(1).add(board[i][j]);
                        }
                    }
                    if(j >=6 && j<9){
                        if (board[i][j] != c) {
                            num3++;
                            list.get(2).add(board[i][j]);
                        }
                    }
                }
                if (i >= 3 && i < 6) {
                    if (j < 3) {
                        if (board[i][j] != c) {
                            num4++;
                            list.get(3).add(board[i][j]);
                        }
                    }
                    if (j >= 3 && j < 6) {
                        if (board[i][j] != c) {
                            num5++;
                            list.get(4).add(board[i][j]);
                        }
                    }
                    if (j >=6 && j<9) {
                        if (board[i][j] != c) {
                            num6++;
                            list.get(5).add(board[i][j]);
                        }
                    }
                }
                if(i >=6 && i<9) {
                    if (j < 3) {
                        if (board[i][j] != c) {
                            num7++;
                            list.get(6).add(board[i][j]);
                        }
                    }
                    if (j >= 3 && j < 6) {
                        if (board[i][j] != c) {
                            num8++;
                            list.get(7).add(board[i][j]);
                        }
                    }
                    if(j >=6 && j<9) {
                        if (board[i][j] != c) {
                            num9++;
                            list.get(8).add(board[i][j]);
                        }
                    }
                }
            }
            if (num >0 && set.size() < num) {
                return false;
            }
        }
        nums.set(0,num1);
        nums.set(1,num2);
        nums.set(2,num3);
        nums.set(3,num4);
        nums.set(4,num5);
        nums.set(5,num6);
        nums.set(6,num7);
        nums.set(7,num8);
        nums.set(8,num9);
        for (int e = 0; e < 9; e++) {
            if (nums.get(e) >0 && list.get(e).size() < nums.get(e)) {
                return false;
            }
            if(tts.get(e)>0 && tlist.get(e).size() < tts.get(e)){
                return false;
            }
        }
        return true;
    }
//图片旋转
    public void rotate(int[][] matrix) {
            int length = matrix.length;
            //对角换
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length-i; j++) {
                    int tem = matrix[i][j];
                    matrix[i][j] = matrix[length-j-1][length-i-1];
                    matrix[length-j-1][length-i-1] = tem;
                }
            }
            //列换
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length/2; j++) {
                    int tem = matrix[j][i];
                    matrix[j][i] = matrix[length-j -1][i];
                    matrix[length-j -1][i] = tem;
                }
            }
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    System.out.print(matrix[i][j]+",");
                }
            }
        }


    /**
     * 快速排序
     */
    public  void quickSort(int[] num,int left,int right){
        //判断num不能为空，长度大于1
        if (left>=right) {
            return;
        }
        int prio = num[left];
        int i = left;
        int j = right;
        while (i<j) {
            while (num[j]>=prio&&i<j){
                j--;
            }
            while (num[i]<=prio&&i<j){
                i++;
            }
            if (i<j) {
                int temp = num[i];
                num[i] = num[j];
                num[j] = temp;
            }
        }

        num[left] = num[i];
        num[i] = prio;
        quickSort(num,left,i-1);
        quickSort(num,i+1,right);
    }


    public static void main(String[] args){
        //char[][] TestLombok = {{'.','.','.','.','.','.','5','.','.'},
        //        {'.','.','.','.','.','.','.','.','.'},
        //        {'.','.','.','.','.','.','.','.','.'},
        //        {'9','3','.','.','2','.','4','.','.'},
        //        {'.','.','7','.','.','.','3','.','.'},
        //        {'.','.','.','.','.','.','.','.','.'},
        //        {'.','.','.','3','4','.','.','.','.'},
        //        {'.','.','.','.','.','3','.','.','.'},
        //        {'.','.','.','.','.','5','2','.','.'}};
        test0 test0 = new test0();
        ////boolean validSudoku = test0.isValidSudoku(TestLombok);
        ////System.out.println(validSudoku);
        //
        //int[][] tt = new int[][]{{ 5, 1, 9,11},
        //        { 2, 4, 8,10},
        //        {13, 3, 6, 7},
        //        {15,14,12,16}};
        //
        //test0.rotate(tt);
        int[] num = {3,45,78,64,52,11,64,55,99,11,18};
        test0.quickSort(num,0,num.length-1);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }
}

class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }
}

