package com.charsmart.data.algorithm.leetcode.dynamicprograme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/28 22:10
 */
public class Dynamic5 {
    /*最长回文子串*/
    public static String longestPalindrome1(String s) {
        if (s.length() == 1) return s;
        if (s.length() == 2) {
            if (s.charAt(0) == s.charAt(1)) return s;
            else return s.substring(1);
        }
        String res = "";
        for (int i = 1; i < s.length() - 1; i++) {
            int left = i - 1;
            int right = i + 1;
            String maxSub = s.substring(i, i + 1);

            char lBorder = s.charAt(i);
            char rBorder = lBorder;
            while (left >= 0 && right <= s.length() - 1) {
                char cl = s.charAt(left);
                char cr = s.charAt(right);
                if (cl == cr) {
                    maxSub = s.substring(left, right + 1);
                    lBorder = rBorder = cl;
                    left--;
                    right++;
                } else {
                    if (onlyOneAlphabet(maxSub) && cl == lBorder) {
                        maxSub = s.substring(left--, right);
                        continue;
                    }
                    if (onlyOneAlphabet(maxSub) && cr == rBorder) {
                        right++;
                        maxSub = s.substring(left + 1, right);
                    } else break;
                }
            }
            boolean onlyOneAlphabet = onlyOneAlphabet(maxSub);
            while (onlyOneAlphabet && left >= 0) {
                if (s.charAt(left) == lBorder) {
                    maxSub = s.charAt(left) + maxSub;
                } else break;
                left--;
            }
            while (onlyOneAlphabet && right <= s.length() - 1) {
                if (s.charAt(right) == rBorder) {
                    maxSub = maxSub + s.charAt(right);
                } else break;
                right++;
            }
            if (maxSub.length() > res.length()) res = maxSub;
        }
        return res;
    }

    static boolean onlyOneAlphabet(String s) {
        char c = s.charAt(0);
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) != c) {
                return false;
            }
        }
        return true;
    }

    static public String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) return s;
        if (length == 2) {
            if (s.charAt(0) == s.charAt(1)) return s;
            else return s.substring(1);
        }
        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }
//        dp[i][j]=(s.charAt(i)==s.charAt(j))&&dp[i+1][j-1]; i+1<j-1 i<j-2
        /*遍历所有子串*/
        int maxLen = 1;
        int maxStart = 0;
        for (int L = 2; L <= length; L++) {
            for (int i = 0; i < length; i++) {
                int j = i + L - 1;
                if (j >= length) break;
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && L > maxLen) {
                    maxLen = L;
                    maxStart = i;
                }
            }
        }
        return s.substring(maxStart, maxLen + maxStart);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(longestPalindrome(line));
        }
    }
}
