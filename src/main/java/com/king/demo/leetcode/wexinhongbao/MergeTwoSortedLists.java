package com.king.demo.leetcode.wexinhongbao;

/**
 * Created by ljq on 2019/3/20 9:30
 * 合并两个排好序的链表
 *  1. 声明一个头结点
 * 2. 将头结点的引用赋值给一个临时结点，也可以叫做下一结点。
 * 3. 进行循环比较，每次都将指向值较小的那个结点(较小值的引用赋值给 lastNode )。
 * 4. 再去掉较小值链表的头结点，指针后移。
 * 5. lastNode 指针也向后移，由于 lastNode 是 head 的引用，这样可以保证最终 head 的值是往后更新的。
 * 6. 当其中一个链表的指针移到最后时跳出循环。
 * 7. 由于这两个链表已经是排好序的，所以剩下的链表必定是最大的值，只需要将指针指向它即可。
 * 8. 由于 head 链表的第一个结点是初始化的0，所以只需要返回 0 的下一个结点即是合并了的链表。
 */
public class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1,ListNode l2){
        ListNode head = new ListNode(0);
        ListNode end = head;
        while (l1 != null && l2 != null){
            if (l1.val < l2.val){
                end.next = l1;
                l1 = l1.next;
            }else {
                end.next = l2;
                l2 = l2.next;
            }
            end = end.next;
        }
        if (l1 == null) {
            end.next = l2;
        }
        if (l2 == null) {
            end.next = l1;
        }

        return head.next;

    }
}

class ListNode{
    int val;
    ListNode next;
    ListNode(int val){
        this.val = val;
    }
}