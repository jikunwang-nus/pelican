package nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/6 14:11
 */
public class Code {


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");
            int T = Integer.parseInt(split[0]);
            int n = Integer.parseInt(split[1]);
            /*n行*/
            int[] cost = new int[n];
            int[] reward = new int[n];
            int[] works = new int[n];
            for (int i = 0; i < n; i++) {
                works[i] = i;
                String cur = reader.readLine();
                String[] splits = cur.split(" ");
                cost[i] = Integer.parseInt(splits[0]);
                reward[i] = Integer.parseInt(splits[1]);
            }
            /*求解最大值*/
            System.out.println(calculateMax(works, T, cost, reward));
        }
    }

    static int calculateMax(int[] src, int leftT, int[] cost, int[] reward) {
        int max = 0;
        if (src.length == 0) return 0;
        if (src.length == 1) {
            if (leftT >= cost[src[0]]) return reward[src[0]];
            else return 0;
        }
        for (int i = 0; i < src.length; i++) {
            int index = src[i];
            int curTimeLeft = leftT;
            int curMax = 0;
            int[] left = leftCopy(src, index);
            if (curTimeLeft >= cost[index]) {
                curMax += reward[index];
                curTimeLeft -= cost[index];
                curMax += calculateMax(left, curTimeLeft, cost, reward);
            }
            max = Math.max(max, curMax);
        }
        return max;
    }

    static int[] leftCopy(int[] src, int n) {
        int[] dst = new int[src.length - 1];
        int index = 0;
        for (int i = 0; i < src.length; i++) {
            if (src[i] != n) {
                dst[index] = src[i];
                index++;
            }
        }
        return dst;
    }

}
