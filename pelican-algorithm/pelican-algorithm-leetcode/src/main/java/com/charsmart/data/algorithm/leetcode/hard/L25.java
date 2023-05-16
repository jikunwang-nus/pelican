package com.charsmart.data.algorithm.leetcode.hard;

import java.util.Stack;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/11 15:10
 */
public class L25 {
    public static void main(String[] args) {
        ListNode listNode = ListNode.constructRow("1,2,3,4,5");
        ListNode node = new L25().reverseKGroup(listNode, 3);
        System.out.println(node);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1) return head;
        ListNode cur = head;
        ListNode resHead = null;
        ListNode resTail = null;
        Stack<ListNode> stack = new Stack<>();
        while (cur != null) {
            /*copy original nodes*/
            ListNode start = cur;
            int count = k;
            while (count > 0 && cur != null) {
                stack.push(cur);
                cur = cur.next;
                count--;
            }
            if (count > 0) {
                resTail.next = start;
                break;
            }
            /*reverse*/
            ListNode sub = null;
            ListNode tail = null;
            while (!stack.isEmpty()) {
                ListNode pop = stack.pop();
                pop.next = null;
                if (sub == null) {
                    sub = pop;
                } else {
                    tail.next = pop;
                }
                tail = pop;
            }
            /*combine*/
            if (resHead == null) {
                resHead = sub;
            } else {
                resTail.next = sub;
            }
            resTail = tail;
        }
        return resHead;
    }
}
