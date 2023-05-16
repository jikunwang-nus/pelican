package com.charsmart.data.algorithm.leetcode.hard;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/11 16:08
 */
public class L44 {
    public static void main(String[] args) {
        int[]input = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        int trap = new L44().trap(input);
        System.out.println(trap);
    }
    public int trap(int[] height) {
        /*find the tallest point in this array*/
        int index = findTopIndex(height, 0, height.length - 1);
        /*处理左边 0- index*/
        int left = calculateLeft(height, index);
        /*处理右边 index- length-1*/
        int right = calculateRight(height, index);
        return left + right;
    }

    private int findTopIndex(int[] values, int start, int end) {
        int top = values[start];
        int index = start;
        for (int i = start; i <= end; i++) {
            int val = values[i];
            if (val > top) {
                index = i;
                top = val;
            }
        }
        return index;
    }

    private int calculateRight(int[] values, int start) {
        /*find the second tall val except top*/
        if (start >= values.length - 1) return 0;
        int secondlyTop = findTopIndex(values, start + 1, values.length - 1);
        return calculateBetweenTops(values, start, secondlyTop) + calculateRight(values, secondlyTop);
    }

    private int calculateBetweenTops(int[] values, int left, int right) {
        int leftBorder = values[left];
        int rightBorder = values[right];
        int height = Math.min(leftBorder, rightBorder);
        return (right - left - 1) * height - sumValues(values, left, right);
    }

    private int sumValues(int[] values, int left, int right) {
        int sum = 0;
        for (int i = left + 1; i < right; i++) {
            sum += values[i];
        }
        return sum;
    }

    private int calculateLeft(int[] values, int end) {
        if (end <= 0) return 0;
        int secondlyTop = findTopIndex(values, 0, end - 1);
        return calculateBetweenTops(values, secondlyTop, end) + calculateLeft(values, secondlyTop);
    }
}
