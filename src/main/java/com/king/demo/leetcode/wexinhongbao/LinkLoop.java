package com.king.demo.leetcode.wexinhongbao;

import lombok.Data;

/**
 * Created by ljq on 2019/3/19 14:18
 */
public class LinkLoop {
    @Data
    public static class Node{
        private Object data;
        public Node next;
    }
    /**
     * 判断链表是否有环，用双指针，快慢指针
     */
    public boolean isLoop(Node node){
        Node slow = node;
        Node fast = node.next;
        while (slow.next!= null){
            Object dataSlow = slow.data;
            Object dataFast = fast.data;
            if (dataFast == dataSlow){
                return true;
            }
            if (fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;

            if (fast == null) {
                return false;
            }
        }
        return false;
    }
}
