package com.charsmart.data.algorithm.leetcode.hard;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/11 14:03
 */
/*
给你一个链表数组，每个链表都已经按升序排列。

请你将所有链表合并到一个升序链表中，返回合并后的链表。

* */

public class L23 {
    public static void main(String[] args) {
        L23 s = new L23();
        ListNode[] listNodes = ListNode.constructArray("[[1,4,5],[1,3,4],[2,6]]");
        ListNode listNode = s.mergeKLists(listNodes);
        System.out.println(listNode);
    }






    public boolean isEmpty(ListNode[] listNodes) {
        for (ListNode node : listNodes) {
            if (node != null) return false;
        }
        return true;
    }

    public ListNode getMinNode(ListNode[] listNodes) {
        if (listNodes.length == 0) return null;
        Integer minVal = null;
        for (ListNode node : listNodes) {
            if (node != null) {
                int val = node.val;
                if (minVal == null) minVal = val;
                else if (val < minVal) {
                    minVal = node.val;
                }
            }
        }
        assert minVal != null;
        ListNode minNode = null;
        ListNode tailNode = null;
        for (int i = 0; i < listNodes.length; i++) {
            ListNode node = listNodes[i];
            if (node != null && node.val == minVal) {
                if (minNode == null) {
                    minNode = node;
                } else {
                    tailNode.next = node;
                }
                tailNode = node;
                listNodes[i] = node.next;
                node.next = null;
            }
        }
        return minNode;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        /*just return null if input array is empty*/
        if (lists.length == 0) return null;
        ListNode head = null;
        ListNode tail = null;
        while (!isEmpty(lists)) {
            ListNode minNode = getMinNode(lists);
            if (head == null) {
                head = minNode;
            } else {
                tail.next = minNode;
            }
            tail = minNode;
            while (minNode.next != null) {
                tail = minNode.next;
                minNode = minNode.next;
            }
        }
        return head;
    }
}
