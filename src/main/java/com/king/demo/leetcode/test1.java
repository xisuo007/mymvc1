package com.king.demo.leetcode;


import java.util.HashMap;

/**
 * Created by ljq on 2019/1/23 11:17
 */
public class test1 {

    /**
     * 查找数据中有没有两个数的和等于target
     */
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> ret = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (ret.containsKey(target-nums[i])) {
                return new int[]{i,ret.get(target-nums[i])};
            }else {
                ret.put(nums[i],i);
            }
        }
        return null;
    }
    /**
    * 9方格数独判断
     */
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            int[] row = new int[9];
            int[] col = new int[9];
            int[] cube = new int[9];
            for (int j = 0; j < 9; j++) {
                if (board[i][j] !='.') {
                    if (row[board[i][j]-'1'] == 1) {
                        return false;
                    }else {
                        row[board[i][j]-'1'] = 1;
                    }
                }
                if (board[j][i] !='.') {
                    if (row[board[j][i]-'1'] == 1) {
                        return false;
                    }else {
                        col[board[j][i]-'1'] = 1;
                    }
                }
                int cubeX = 3*(i/3)+j/3;
                int cubeY = 3*(i%3)+j%3;
                if(board[cubeX][cubeY]!='.'){
                    if (cube[board[cubeX][cubeY]-'1'] == 1) {
                        return false;
                    }else {
                        cube[board[cubeX][cubeY]-'1'] = 1;
                    }
                }
            }

        }
        return true;
    }

    /**
     * 查找一个有序数组中某个数第一次出现的下标
     */
    public int searchIndex(int key, Integer[] arr){
        if (arr == null && arr.length == 0) {
            return -1;
        }
        int start = 0;
        int end = arr.length - 1;
        if (arr[start] > key || arr[end] < key) {
            return -1;
        }
        int mid = 0;
        while (start <= end){
            mid = (start + end)/2;
            if(arr[mid] > key){
                end = mid-1;
            }else if (arr[mid] < key){
                start = mid +1;
            }else {
                while (mid -1 >= 0 && arr[mid -1]==key){
                    mid--;
                }
                return mid;
            }
        }
        return -1;
    }

    /**
     * 旋转图像
     */
    public void rotate(int[][] matrix){
        if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
            return;
        }
        int n = matrix.length -1;
        int m = matrix.length -1;
        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                int temp = matrix[n-j][i];
                matrix[n-j][i]=matrix[n-i][n-j];
                matrix[n-i][n-j]=matrix[j][n-i];
                matrix[j][n-i] = matrix[i][j];
                matrix[i][j]=temp;
            }
            m--;
        }
    }

    public static void main(String[] args){
        char[][] test =
               {{'.','.','.','.','.','.','5','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'9','3','.','.','2','.','4','.','.'},
                {'.','.','7','.','.','.','3','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'.','.','.','3','4','.','.','.','.'},
                {'.','.','.','.','.','3','.','.','.'},
                {'.','.','.','.','.','5','2','.','.'}};
        test1 test0 = new test1();
        //boolean validSudoku = test0.isValidSudoku(TestLombok);
        //System.out.println(validSudoku);
        Integer[] ints = new Integer[]{2, 3, 4, 5, 6, 8, 9, 9, 10, 12, 17, 17, 20, 20, 20, 21};
        int i = test0.searchIndex(9, ints);
        System.out.println(i);
    }
}
