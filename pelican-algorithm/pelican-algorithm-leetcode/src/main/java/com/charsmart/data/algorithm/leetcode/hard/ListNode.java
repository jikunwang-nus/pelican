package com.charsmart.data.algorithm.leetcode.hard;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/11 15:11
 */
public class ListNode {
    int val;
    public ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode constructRow(String str) {
        String[] split = str.split(",");
        if (split.length == 0) return null;
        ListNode head = null;
        ListNode tail = null;
        for (String s : split) {
            ListNode node = new ListNode(Integer.parseInt(s));
            if (head == null) {
                head = node;
                tail = head;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        return head;
    }

    public static ListNode[] constructArray(String input) {
        int start = 0;
        int end;
        String sub;
        List<ListNode> list = new ArrayList<>();
        for (int i = 1; i < input.length() - 1; i++) {
            char c = input.charAt(i);
            if (c == '[') start = i + 1;
            else if (c == ']') {
                end = i;
                sub = input.substring(start, end);
                ListNode node = constructRow(sub);
                list.add(node);
            }
        }
        return list.toArray(new ListNode[0]);
    }
}
