package nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/6 14:47
 */
public class CodeBackUp {
    Scanner in = Entry.std();

    void s1() {
//        明明的随机数
        BitSet bitSet = new BitSet(500);
        int count = Integer.parseInt(in.nextLine());
        while (in.hasNextLine() && count > 0) {
            try {
                int i = Integer.parseInt(in.nextLine());
                bitSet.set(i);
            } finally {
                count--;
            }
        }
        for (int i = 1; i <= 500; i++) {
            if (bitSet.get(i)) System.out.println(i);
        }
    }

    void s2() {
//      进制转换 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示
        Scanner in = new Scanner(System.in);
        String s = null;
        if (in.hasNext()) {
            s = in.next();
        }
        if (s == null || !s.startsWith("0x")) return;
        String valueStr = s.substring(2);
        byte[] bytes = valueStr.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        int count = length;
        int sum = 0;
        while (count > 0) {
            byte b = bytes[count - 1];
            int curValue;
            if (b >= 65) {
                curValue = b - 55;
            } else curValue = b - 48;
            sum += curValue * Math.pow(16, length - count);
            count--;
        }
        System.out.println(sum);
    }

    static int calculate(int ep) {
        int drink = 0;
        while (ep >= 2) {
            if (ep > 3) {
                ep -= 2;
                drink++;
            } else {
                drink++;
                ep -= 2;
            }
        }
        return drink;
    }

    //    明明的汽水瓶
    void s3() {
        Scanner in = new Scanner(System.in);
        List<Integer> res = new ArrayList<>();
        while (in.hasNext()) {
            int i = in.nextInt();
            if (i == 0) break;
            res.add(calculate(i));
        }
        for (Integer re : res) {
            System.out.println(re);
        }
    }

    //    取整
    void s4() {
        Scanner in = new Scanner(System.in);
        if (in.hasNext()) {
            String next = in.next();
            String[] split = next.split("\\.");
            String scale = split[1];
            if (scale.getBytes(StandardCharsets.UTF_8)[0] < (byte) '5')
                System.out.println(split[0]);
            else
                System.out.println(Integer.parseInt(split[0]) + 1);
        }
    }

    //    提取不重复的数
    void s5() {
        Scanner in = new Scanner(System.in);
        boolean[] exist = new boolean[10];
        String res = "";
        if (in.hasNext()) {
            String str = in.next();
            byte[] bytes = str.getBytes();
            for (int i = bytes.length; i > 0; i--) {
                int val = bytes[i - 1] - (byte) '0';
                if (exist[val]) continue;
                exist[val] = true;
                res += (char) bytes[i - 1];
            }
        }
        System.out.println(res);
    }

    //    HJ46 截取字符串
    void s6() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.next();
        int k = in.nextInt();
        char[] chars = str.toCharArray();
        char[] res = new char[k];
        for (int i = 0; i < k; i++) res[i] = chars[i];
        System.out.println(String.valueOf(res));
    }

    //    HJ58 输入n个整数，输出其中最小的k个
    void s7() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        Integer[] minHeap = new Integer[n];
        int tmp = n;
        while (tmp > 0) {
            int val = in.nextInt();
            minHeap[n - tmp] = val;
            tmp--;
        }
        Arrays.sort(minHeap);
        for (int i = 0; i < k; i++) {
            System.out.print(minHeap[i] + " ");
        }
    }

    static class MinHeap {
        public Integer min;
        public Integer max;
        private final Integer[] data;
        private int count;

        boolean isFull() {
            return count >= data.length;
        }

        public void print() {
            String process = "[";
            for (Integer datum : data) {
                process += datum + " ";
            }
            process += "]";
            System.out.println(process);
        }

        public void printCurrent(int val) {
            String process = val + "[" + min + "~" + max + "]" + "-[";
            for (Integer datum : data) {
                process += datum + " ";
            }
            process += "]";
            System.out.println(process);
        }

        MinHeap(int k) {
            data = new Integer[k];
        }

        public void add(int val) {
            /*初始化为空时*/
            if (min == null || max == null) {
                data[0] = val;
                min = val;
                max = min;
                count++;
                return;
            }
            if (val <= min) {
                min = val;
                if (isFull()) {
                    System.arraycopy(data, 0, data, 1, data.length - 1);
                } else {
                    System.arraycopy(data, 0, data, 1, count);
                }
                data[0] = val;
                if (!isFull()) count++;
                return;
            }
            if (val > max) {
                if (isFull()) return;
                data[count++] = val;
                max = val;
                return;
            }
            entry(val);
        }

        void entry(int val) {
            int border = Math.min(count, data.length);
            for (int i = 0; i < border - 1; i++) {
                if (val > data[i] && val <= data[i + 1]) {
                    if (isFull()) {
                        System.arraycopy(data, i + 1, data, i + 2, border - i - 2);
                    } else {
                        System.arraycopy(data, i + 1, data, i + 2, border - i - 1);
                    }
                    data[i + 1] = val;
                    break;
                }
            }
            if (!isFull()) count++;
            max = data[Math.min(count, data.length) - 1];
        }
    }

    void s7_2() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        MinHeap heap = new MinHeap(k);
        int tmp = n;
        while (tmp > 0) {
            int val = in.nextInt();
            heap.add(val);
//            heap.printCurrent(val);
            tmp--;
        }
        for (int i = 0; i < k; i++) {
            System.out.print(heap.data[i] + " ");
        }
    }

    //
    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //正反排序
    void s8() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int size = in.nextInt();
        int[] arr = new int[size];
        int count = size;
        while (count > 0) {
            int val = in.nextInt();
            arr[size - count] = val;
            count--;
        }
        boolean reverse = in.nextInt() != 0;
        if (!reverse) {
            for (int i = size - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (arr[j] > arr[j + 1]) swap(arr, j, j + 1);
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = size - 1; j > i; j--) {
                    if (arr[j - 1] < arr[j]) swap(arr, j - 1, j);
                }
            }
        }
        for (int val : arr) {
            System.out.print(val + " ");
        }
    }

    //    最后一个单词长度
    void s9() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            int index = line.lastIndexOf(" ");
            System.out.println(line.substring(index + 1));
        }
    }

    //    HJ2 计算某字符出现次数
    void s10() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        String next = in.next();
        byte b = next.getBytes(StandardCharsets.UTF_8)[0];
        int interval = (byte) 'a' - (byte) 'A';
        boolean isa = b >= (byte) 'a' && b <= (byte) 'z';
        boolean isA = b >= (byte) 'A' && b <= (byte) 'Z';
        int count = 0;
        byte[] bytes = str.getBytes();
        for (byte val : bytes) {
            if (isA && (val == b || val == b + interval)) {
                count++;
            } else if (isa && (val == b || val == b - interval)) {
                count++;
            } else if (val == b) count++;
        }
        System.out.print(count);
    }

    //    HJ4 字符串分隔
    void s11() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        List<String> res = new ArrayList<>();
        int length = str.length();
        int left = length & 7;
        int fill = left == 0 ? 0 : 8 - left;
        for (int i = 0; i < fill; i++) {
            str += '0';
        }
        while (str.length() > 0) {
            String sub = str.substring(0, 8);
            str = str.substring(8);
            res.add(sub);
        }
        for (String re : res) {
            System.out.println(re);
        }
    }

    //    HJ21 简单密码
    void s12() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        char[] chars = str.toCharArray();
        int inv = 'a' - 'A';
        char[] res = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            char tmp = 0;
            if (ch >= 'a' && ch <= 'z') {
                int interval = ch - 'a';
                if (interval <= 2) tmp = '2';
                if (interval >= 3 && interval <= 5) tmp = '3';
                if (interval >= 6 && interval <= 8) tmp = '4';
                if (interval >= 9 && interval <= 11) tmp = '5';
                if (interval >= 12 && interval <= 14) tmp = '6';
                if (interval >= 15 && interval <= 18) tmp = '7';
                if (interval >= 19 && interval <= 21) tmp = '8';
                if (interval >= 22) tmp = '9';
            } else if (ch >= 'A' && ch <= 'Z') {
                tmp = (char) (ch + inv);
                if (tmp == 'z') tmp = 'a';
                else tmp++;
            } else
                tmp = ch;
            res[i] = tmp;
        }
        System.out.println(String.valueOf(res));
    }

    //    HJ23 删除字符串中出现次数最少的字符
    void s13() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        char[] chars = str.toCharArray();
        byte[] countArray = new byte[26];
        for (char ch : chars) {
            countArray[ch - 'a']++;
        }
        int minCount = 20;
        for (byte b : countArray) {
            if (b > 0 && b < minCount) {
                minCount = b;
            }
        }
        List<Character> minChars = new ArrayList<>();
        for (int i = 0; i < countArray.length; i++) {
            if (countArray[i] == minCount) minChars.add((char) ('a' + i));
        }
        char[] res = new char[chars.length];
        int index = 0;
        for (char ch : chars) {
            if (!minChars.contains(ch)) {
                res[index] = ch;
                index++;
            }
        }
        System.out.println(String.valueOf(res).substring(0, index));
    }

    void s14() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        List<String> res = new ArrayList<>();
        char[] chars = str.toCharArray();
        String tmp = "";
        for (char ch : chars) {
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                tmp += ch;
            } else if (!"".equals(tmp)) {
                res.add(tmp);
                tmp = "";
            }
        }
        if (!"".equals(tmp)) res.add(tmp);
        for (int i = res.size() - 1; i >= 0; i--) {
            System.out.print(res.get(i) + " ");
        }
    }

    //    HJ34 图片整理
    void s15() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String res = String.valueOf(chars);
        System.out.println(res);
    }

    //    HJ35 蛇形矩阵
    void s16() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[][] arr = new int[n][n];
        arr[0][0] = 1;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                for (int j = 1; j < n; j++) {
                    arr[0][j] = arr[0][j - 1] + j + 1;
                }
            } else {
                for (int j = 0; j < n - i; j++) {
                    arr[i][j] = arr[i - 1][j + 1] - 1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int[] cur = arr[i];
            for (int val : cur) {
                if (val > 0) {
                    System.out.print(val + " ");
                } else {
                    break;
                }
            }
            System.out.print("\n");
        }
    }

    //    HJ37 统计每个月兔子的总数
    void s17() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int n = Integer.parseInt(line);
            int sum = 1;
            int oneOld = 0;
            int twoOld = 0;
            int adult = 1;
            if (n < 3) {
                System.out.println(sum);
                return;
            }
            int temp = 3;
            while (temp <= n) {
                adult += twoOld;
                twoOld = oneOld;
                int newOneOld = adult;
                oneOld = newOneOld;
                temp++;
                if (newOneOld > 0) sum += newOneOld;
            }
            System.out.println(sum);
        }
    }

    //    HJ38 求小球落地5次后所经历的路程和第5次反弹的高度
    void s18() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            double h = Double.parseDouble(line);
            int cycle = 5;
            double route = 0.0;
            while (cycle > 0) {
                route += h * 1.5;
                h = h * 0.5;
                cycle--;
            }
            System.out.println(route - h);
            System.out.println(h);
        }
    }

    static boolean verifyMask(int mask) {
        if (mask == 0 || mask == 0xFFFFFFFF) {
            return false;
        }
        int tmp = 32;
        boolean borderFlag = false;
        while (tmp > 0) {
            int val = mask & 1;
            if (borderFlag && val == 0) {
                return false;
            }
            if (val == 1) {
                borderFlag = true;
            }
            mask = mask >> 1;
            tmp--;
        }
        return true;
    }

    static int mergeToInt(String[] split) {
        int res = 0;
        for (int i = split.length - 1; i >= 0; i--) {
            int value = Integer.parseInt(split[i]);
            res = res | value << 8 * (split.length - 1 - i);
        }
        return res;
    }

    static boolean verifyFormat(String[] ipSplit) {
        if (ipSplit.length != 4) {
            return false;
        }
        for (String val : ipSplit) {
            int value = Integer.parseInt(val);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }

    //    HJ18 识别有效的IP地址和掩码并进行分类统计
    void s19() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int[] res = new int[7];
        while ((line = br.readLine()) != null) {
            String[] split = line.split("~");
            if (split.length != 2) {
                res[5]++;
                continue;
            }
            String ipStr = split[0];
            String[] ipSplit = ipStr.split("\\.");
            if (!verifyFormat(ipSplit)) {
                res[5]++;
                continue;
            }
            String maskStr = split[1];
            String[] maskSplit = maskStr.split("\\.");
            if (!verifyFormat(maskSplit)) {
                res[5]++;
                continue;
            }
            int ip = mergeToInt(ipSplit);
//           判断是否是私网
            int h16 = ip >>> 16;
            int h8 = h16 >>> 8;
            int l8 = h16 & 255;
            if (h8 == 0 || h8 == 127) continue;
            if (!verifyMask(mergeToInt(maskSplit))) {
                res[5]++;
                continue;
            }
            if (h8 == 10 || (h8 == 172 && (l8 >= 16 && l8 < 32)) || (h8 == 192 && l8 == 168)) res[6]++;
//            区分ip类型
            if (h8 <= 126) res[0]++;
            else if (h8 <= 191) res[1]++;
            else if (h8 <= 223) res[2]++;
            else if (h8 <= 239) res[3]++;
            else res[4]++;
        }
        for (int re : res) {
            System.out.print(re + " ");
        }
    }

    //    HJ19 简单错误记录
    void s20() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Map<String, Integer> map = new LinkedHashMap<>();
        Deque<String> queue = new ArrayDeque<>(8);
        Set<String> oldKey = new HashSet<>();
        while ((line = br.readLine()) != null) {
            String[] split = line.split(" ");
            String filePath = split[0];
            int index = filePath.lastIndexOf("\\");
            String fileName = filePath.substring(index + 1);
            if (fileName.length() > 16) fileName = fileName.substring(
                    fileName.length() - 16);
            String key = fileName + " " + split[1];
            if (oldKey.contains(key)) continue;
            Integer value = map.get(key);
            if (value == null) {
                map.put(key, 1);
                if (queue.size() >= 8) {
                    String disabledKey = queue.poll();
                    oldKey.add(disabledKey);
                }
                queue.offer(key);
            } else {
                map.put(key, value + 1);
            }
        }
        while (!queue.isEmpty()) {
            String key = queue.poll();
            Integer value = map.get(key);
            System.out.println(key + " " + value);
        }
    }

    void s20_2() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Map<String, Integer> map = new LinkedHashMap<>();
        while ((line = br.readLine()) != null) {
            String[] split = line.split(" ");
            String filePath = split[0];
            int index = filePath.lastIndexOf("\\");
            String fileName = filePath.substring(index + 1);
            if (fileName.length() > 16) fileName = fileName.substring(
                    fileName.length() - 16);
            String key = fileName + " " + split[1];
            Integer value = map.getOrDefault(key, 0);
            map.put(key, value + 1);
        }
        int index = 0;
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            if (index >= entries.size() - 8) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            index++;
        }
    }

    //    HJ20 密码验证合格程序
    void s21() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        OUT:
        while ((line = br.readLine()) != null) {

            char[] chars = line.toCharArray();
            if (chars.length < 8) {
                System.out.println("NG");
                continue;
            }
            boolean cap = false, lowercase = false, number = false, extra = false;
            for (char ch : chars) {
                if (ch >= 'A' && ch <= 'Z') cap = true;
                else if (ch >= 'a' && ch <= 'z') lowercase = true;
                else if (ch >= '0' && ch <= '9') number = true;
                else if (ch != ' ' && ch != '\n') {
                    extra = true;
                }
            }
            int satisfy = (cap ? 1 : 0) + (lowercase ? 1 : 0) + (number ? 1 : 0) +
                    (extra ? 1 : 0);
            if (satisfy < 3) {
                System.out.println("NG");
                continue;
            }
            for (int i = 0; i < line.length() - 2; i++) {
                String sub = line.substring(i, i + 3);
                String left = line.substring(i + 3);
                if (left.contains(sub)) {
                    System.out.println("NG");
                    continue OUT;
                }
            }
            System.out.println("OK");
        }
    }

    //    统计
    void s22() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            Map<Character, Integer> map = new LinkedHashMap<>();
            char[] chars = line.toCharArray();
            for (char ch : chars) {
                Integer value = map.getOrDefault(ch, 0);
                map.put(ch, value + 1);
            }
            TreeMap<Integer, List<Character>> valueMap = new TreeMap<>();
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                Character k = entry.getKey();
                Integer v = entry.getValue();
                List<Character> charList;
                charList = valueMap.getOrDefault(v, new ArrayList<>());
                charList.add(k);
                charList.sort(Character::compareTo);
                valueMap.put(v, charList);
            }
            while (!valueMap.isEmpty()) {
                Map.Entry<Integer, List<Character>> entry = valueMap.pollLastEntry();
                List<Character> value = entry.getValue();
                for (Character character : value) {
                    System.out.print(character);
                }
            }
        }
    }

    //    HJ40 统计字符
    void s23() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int[] res = new int[4];
            char[] chars = line.toCharArray();
            for (char ch : chars) {
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) res[0]++;
                else if (ch == ' ') res[1]++;
                else if (ch >= '0' && ch <= '9') res[2]++;
                else res[3]++;
            }
            for (int re : res) {
                System.out.println(re);
            }
        }
    }

    static class Node {
        int value;
        public Node next;

        Node(int value) {
            this.value = value;
        }
    }

    //    HJ51 输出单向链表中倒数第k个结点
    void s24() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int state = 0;
        int k = 0;
        String nodes = "";
        while ((line = reader.readLine()) != null) {
            if (state == 0) {
                state++;
            } else if (state == 1) {
                nodes = line;
                state++;
            } else if (state == 2) {
                k = Integer.parseInt(line);
                //        构建单链表
                String[] numberArr = nodes.split(" ");
                Node head = new Node(Integer.parseInt(numberArr[0]));
                Node tmp = head;
                for (int i = 1; i < numberArr.length; i++) {
                    Node node = new Node(Integer.parseInt(numberArr[i]));
                    tmp.next = node;
                    tmp = node;
                }
                tmp = head;
//        滑动窗口
                Node ws = tmp;
                Node we = tmp;
                while (tmp != null) {
                    tmp = tmp.next;
                    if (k > 0) {
                        we = we.next;
                        k--;
                    } else {
                        ws = ws.next;
                        we = we.next;
                    }
                }
                System.out.println(ws.value);
                state = 0;
                k = 0;
                nodes = "";
            }
        }
    }

    static int computeMinimum(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (min == max) {
            return min;
        }
        if (max % min == 0) return max;
        if (max - min == 1) return min * max;
        /*计算公约数*/
        int res = 1;
        for (int i = 2; i < min / 2; i++) {
            if (min % i == 0 && max % i == 0) {
                res *= i;
            }
        }
        return min * max / res;
    }

    //    HJ108 求最小公倍数
    void s25() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            System.out.println(computeMinimum(a, b));
        }
    }

    //    逆序输出字符串
    void s26() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            Stack<Character> stack = new Stack<>();
            for (char c : line.toCharArray()) {
                stack.push(c);
            }
            while (!stack.isEmpty()) System.out.print(stack.pop());
        }
    }

    //HJ105 记负均正II
    void s27() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int positiveCount = 0;
        int negativeCount = 0;
        int positiveSum = 0;
        while ((line = reader.readLine()) != null) {
            int v = Integer.parseInt(line);
            if (v < 0) negativeCount++;
            else {
                positiveCount++;
                positiveSum += v;
            }
        }
        System.out.println(negativeCount);
        if (positiveCount == 0) System.out.println(0.0);
        else {
            long v = Math.round(positiveSum * 10.0 / positiveCount);
            System.out.println(v / 10 + "." + v % 10);
        }
    }

    //    HJ30 字符串合并处理
    void s28() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int index = line.indexOf(" ");
            String str = line.substring(0, index) + line.substring(index + 1);
            boolean odd = (str.length() & 1) == 0;
            char[] chars = str.toCharArray();
            for (int i = str.length() - 1; i > 0; i -= 2) {
                for (int j = odd ? 1 : 0; j < i; j += 2) {
                    if (chars[j] > chars[j + 2]) {
                        char tmp = chars[j];
                        chars[j] = chars[j + 2];
                        chars[j + 2] = tmp;
                    }
                }
            }
            for (int i = str.length() - 2; i > 0; i -= 2) {
                for (int j = odd ? 0 : 1; j < i; j += 2) {
                    if (chars[j] > chars[j + 2]) {
                        char tmp = chars[j];
                        chars[j] = chars[j + 2];
                        chars[j + 2] = tmp;
                    }
                }
            }
            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];
                if ((ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F') || (ch >= '0' && ch <= '9')) {
                    int val;
                    if (ch <= '9') {
                        val = ch - '0';
                    } else if (ch >= 'a') {
                        val = ch - 'a' + 10;
                    } else {
                        val = ch - 'A' + 10;
                    }
                    /*颠倒顺序*/
                    int convert = ((val & 1) << 3) + ((val & 2) << 1) + ((val & 4) >> 1) + ((val & 8) >> 3);
                    char nc;
                    if (convert < 10) {
                        nc = (char) ('0' + convert);
                    } else {
                        nc = (char) ('A' + convert - 10);
                    }
                    chars[i] = nc;
                }
            }
            System.out.println(String.valueOf(chars));
        }
    }

    //    HJ32 密码截取
    void s29() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null && !"".equals(line)) {
            int max = 1;
            if (line.length() == 1) {
                System.out.println(max);
                continue;
            }
            if (line.length() == 2 && line.charAt(0) == line.charAt(1)) {
                System.out.println(2);
                continue;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int len = 1;
                int index = i;
                while (index < line.length() - 1 && c == line.charAt(index + 1)) {
                    index++;
                    len++;
                    if (len > max) max = len;
                }
                int right = line.length() - 1 - index;
                int border = Math.min(i, right);
                int tmp = border;
                while (tmp > 0) {
                    if (line.charAt(i - (border - tmp + 1)) == line.charAt(index + (border - tmp + 1))) {
                        len += 2;
                        if (len > max) max = len;
                    } else break;
                    tmp--;
                }
                if (i < index) i = index;
            }
            System.out.println(max);
        }
    }

    //    HJ33 整数与IP地址间的转换
    void s30() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null && !"".equals(line)) {
            try {
                long value = Long.parseLong(line);
                int h8 = (int) ((value & 0xFF000000) >>> 24);
                int h16 = (int) ((value & 0xFF0000) >>> 16);
                int l16 = (int) ((value & 0xFF00) >>> 8);
                int l8 = (int) (value & 0xFF);
                System.out.println(h8 + "." + h16 + "." + l16 + "." + l8);
            } catch (Exception ex) {
                String[] split = line.split("\\.");
                long h8 = Long.parseLong(split[0]) << 24;
                int h16 = Integer.parseInt(split[1]) << 16;
                int l16 = Integer.parseInt(split[2]) << 8;
                int l8 = Integer.parseInt(split[3]);
                System.out.println(h8 | h16 | l16 | l8);
            }
        }
    }

    //    HJ90 合法IP
    void s31() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        OUT:
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\.");
            if (split.length != 4) {
                System.out.println("NO");
                continue;
            }
            for (String s : split) {
                try {
                    int value = Integer.parseInt(s);
                    if (!s.equals(String.valueOf(value))) {
                        System.out.println("NO");
                        continue OUT;
                    }
                    if (value < 0 || value > 255) {
                        System.out.println("NO");
                        continue OUT;
                    }
                } catch (Exception ex) {
                    System.out.println("NO");
                    continue OUT;
                }
            }
            System.out.println("YES");
        }
    }

    static int calculate(int n, int m) {
        if (n == 1 || m == 1) return 1;
        return calculate(n - 1, m) + calculate(n, m - 1);
    }

    //    HJ91 走方格的方案数
    void s32() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splits = line.split(" ");
            int n = Integer.parseInt(splits[0]) + 1;
            int m = Integer.parseInt(splits[1]) + 1;
            int res = calculate(n, m);
            System.out.println(res);
        }
    }

    //    HJ26 字符串排序
    void s33() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            char[] chars = line.toCharArray();
            /*记录index*/
            List<Integer> indexes = new ArrayList<>();
            List<ArrayDeque<Character>> alphabet = new ArrayList<>(26);
            for (int i = 0; i < 26; i++) {
                alphabet.add(new ArrayDeque<>());
            }
            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                    indexes.add(i);
                    int index;
                    if (ch < 'a') {
                        index = ch - 'A';
                    } else {
                        index = ch - 'a';
                    }
                    ArrayDeque<Character> queue = alphabet.get(index);
                    queue.offer(ch);
                }
            }
            Iterator<Integer> iterator = indexes.iterator();
            for (ArrayDeque<Character> characters : alphabet) {
                while (!characters.isEmpty()) {
                    chars[iterator.next()] = characters.poll();
                }
            }
            System.out.println(String.valueOf(chars));
        }
    }

    //    HJ98 自动售货系统
    void s34() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] orders = line.split(";");
            String initial = orders[0];
            String[] initials = initial.split(" ");
            /*初始化name*/
            List<String> goods = Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6");
            /*初始化单价*/
            int[] prices = new int[6];
            prices[0] = 2;
            prices[1] = 3;
            prices[2] = 4;
            prices[3] = 5;
            prices[4] = 8;
            prices[5] = 6;
            /*初始化数量*/
            int[] number = new int[6];
            int summaryCount = 0;
            String[] priceArray = initials[1].split("-");
            for (int i = 0; i < priceArray.length; i++) {
                number[i] = Integer.parseInt(priceArray[i]);
                summaryCount += number[i];
            }
            /*初始化钱盒*/
            int[] changes = new int[4];
            String change = initials[2];
            String[] changeArray = change.split("-");
            for (int i = 0; i < changeArray.length; i++) {
                changes[changeArray.length - 1 - i] = Integer.parseInt(changeArray[i]);
            }
            /*初始化成功*/
            System.out.println("S001:Initialization is successful");
            int userBalance = 0;
            /*处理下面的指令*/
            for (String order : orders) {
                if ("c".equals(order)) {
                    /*处理退币*/
                    if (userBalance <= 0) {
                        System.out.println("E009:Work failure");
                        continue;
                    }
                    int ret[] = new int[4];
                    while (userBalance >= 10 && changes[0] > 0) {
                        userBalance -= 10;
                        changes[0]--;
                        ret[3]++;
                    }
                    while (userBalance >= 5 && changes[1] > 0) {
                        userBalance -= 5;
                        changes[1]--;
                        ret[2]++;
                    }
                    while (userBalance >= 2 && changes[2] > 0) {
                        userBalance -= 2;
                        changes[2]--;
                        ret[1]++;
                    }
                    while (userBalance >= 1 && changes[3] > 0) {
                        userBalance -= 1;
                        changes[3]--;
                        ret[0]++;
                    }
                    System.out.println("1 yuan coin number=" + ret[0]);
                    System.out.println("2 yuan coin number=" + ret[1]);
                    System.out.println("5 yuan coin number=" + ret[2]);
                    System.out.println("10 yuan coin number=" + ret[3]);

                } else {
                    if (order.startsWith("p")) {
                        /*处理投币*/
                        String[] orderSplit = order.split(" ");
                        int val = Integer.parseInt(orderSplit[1]);
                        if (val != 1 && val != 2 && val != 5 && val != 10) {
                            System.out.println("E002:Denomination error");
                            continue;
                        }
                        if (val > 2 && (changes[2] * 2 + changes[3] < val)) {
                            System.out.println("E003:Change is not enough, pay fail");
                            continue;
                        }
                        if (summaryCount <= 0) {
                            System.out.println("E005:All the goods sold out");
                            continue;
                        }
                        /*处理投币成功*/
                        switch (val) {
                            case 10:
                                changes[0]++;
                                break;
                            case 5:
                                changes[1]++;
                                break;
                            case 2:
                                changes[2]++;
                                break;
                            default:
                                changes[3]++;
                        }
                        userBalance += val;
                        System.out.println("S002:Pay success,balance=" + userBalance);
                    } else if (order.startsWith("b")) {
                        String[] orderSplit = order.split(" ");
                        String name = orderSplit[1];
                        if (!goods.contains(name)) {
                            System.out.println("E006:Goods does not exist");
                            continue;
                        }
                        int nameNumber = Integer.parseInt(name.substring(1));
                        if (number[nameNumber - 1] <= 0) {
                            System.out.println("E007:The goods sold out");
                            continue;
                        }
                        int price = prices[nameNumber - 1];
                        if (userBalance < price) {
                            System.out.println("E008:Lack of balance");
                            continue;
                        }
                        userBalance -= price;
                        number[nameNumber - 1]--;
                        System.out.println("S003:Buy success,balance=" + userBalance);
                    } else if (order.startsWith("q")) {
                        String[] orderSplit = order.split(" ");
                        if (orderSplit.length != 2) {
                            System.out.println("E010:Parameter error");
                            continue;
                        }
                        int type = Integer.parseInt(orderSplit[1]);
                        if (type != 0 && type != 1) {
                            System.out.println("E010:Parameter error");
                            continue;
                        }
                        if (type == 0) {
                            for (int i = 0; i < 6; i++) {
                                int index = i + 1;
                                System.out.println("A" + index + " " + prices[i] + " " + number[i]);
                            }
                        } else {
                            System.out.println("1 yuan coin number=" + changes[3]);
                            System.out.println("2 yuan coin number=" + changes[2]);
                            System.out.println("5 yuan coin number=" + changes[1]);
                            System.out.println("10 yuan coin number=" + changes[0]);
                        }
                    }
                }
            }
        }
    }

    //    HJ99 自守数
    void s35() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int n = Integer.parseInt(line);
            int count = 0;
            for (int i = 0; i <= n; i++) {
                if (isSelfGuard(i)) count++;
            }
            System.out.println(count);
        }
    }

    static boolean isSelfGuard(int n) {
        String ns = String.valueOf(n);
        String power = String.valueOf(n * n);
        String substring = power.substring(power.length() - ns.length());
        return substring.equals(ns);
//        return (((n * n) & n) ^ n) == 0;
    }

    static long verifyFormat(String input) {
        String[] split = input.split("\\.");
        if (split.length != 4) return -1;
        for (String s : split) {
            int value = Integer.parseInt(s);
            if (value < 0 || value > 255) return -1;
        }
        return ((long) Integer.parseInt(split[0]) << 24)
                | (Integer.parseInt(split[1]) << 16)
                | (Integer.parseInt(split[2]) << 8)
                | Integer.parseInt(split[3]);
    }

    //    HJ39 判断两个IP是否属于同一子网
    void s39() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        OUTER:
        while ((line = reader.readLine()) != null) {
            String maskStr = line;
            String ipStr1 = reader.readLine();
            String ipStr2 = reader.readLine();
            long ipValue1 = verifyFormat(ipStr1);
            if (ipValue1 == -1) {
                System.out.println(1);
                continue;
            }
            long ipValue2 = verifyFormat(ipStr2);
            if (ipValue2 == -1) {
                System.out.println(1);
                continue;
            }
            long maskValue = verifyFormat(maskStr);
            if (maskValue == -1) {
                System.out.println(1);
                continue;
            }
            /*校验子网掩码合法*/
            if (maskValue == 0) {
                System.out.println(1);
                continue;
            }
            int tmp = 32;
            long tmpValue = maskValue;
            boolean border = false;
            while (tmp > 0) {
                if ((tmpValue & 1) == 1) border = true;
                if ((tmpValue & 1) == 0 && border) {
                    System.out.println(1);
                    continue OUTER;
                }
                tmpValue >>= 1;
                tmp--;
            }
            System.out.println((ipValue1 & maskValue) == (ipValue2 & maskValue) ? 0 : 2);
        }
    }

    static String standardXXX(int n) {
        if (n >= 100) {
            int val = n / 100;
            int left = n % 100;
            if (left == 0) {
                return standardX(val) + " hundred";
            }
            return standardX(val) + " hundred and " + standardXX(left);
        } else if (n >= 10) {
            return standardXX(n);
        } else return standardX(n);
    }

    static String standardX(int n) {
        if (n == 0) return "zero";
        switch (n) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            case 8:
                return "eight";
            default:
                return "nine";
        }
    }

    static String standardXX(int n) {
        switch (n) {
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
            case 13:
                return "thirteen";
            case 14:
                return "fourteen";
            case 15:
                return "fifteen";
            case 16:
                return "sixteen";
            case 17:
                return "seventeen";
            case 18:
                return "eighteen";
            case 19:
                return "nineteen";
            default:
                int val = n / 10;
                int left = n % 10;
                String mid = "";
                switch (val) {
                    case 0:
                        String tail = standardX(left);
                        if (!"zero".equals(tail)) {
                            mid = tail;
                        }
                        return mid;
                    case 2:
                        mid = "twenty";
                        break;
                    case 3:
                        mid = "thirty";
                        break;
                    case 4:
                        mid = "forty";
                        break;
                    case 5:
                        mid = "fifty";
                        break;
                    case 6:
                        mid = "sixty";
                        break;
                    case 7:
                        mid = "seventy";
                        break;
                    case 8:
                        mid = "eighty";
                        break;
                    case 9:
                        mid = "ninety";
                        break;
                }
                String tail = standardX(left);
                if (!"zero".equals(tail)) {
                    mid += " " + tail;
                }
                return mid;

        }
    }

    //    HJ42 学英语
    void s36() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int length = line.length();
            long l = Long.parseLong(line);
            if (length <= 3) {
                String tmp;
                System.out.println((tmp = standardXXX((int) l)) == null ? "zero" : tmp);
            } else if (length <= 6) {
                String l2 = line.substring(0, line.length() - 3);
                String l1 = line.substring(line.length() - 3);
                System.out.println(standardXXX(Integer.parseInt(l2)) + " thousand "
                        + standardXXX(Integer.parseInt(l1)));
            } else if (length <= 9) {
                String l3 = line.substring(0, line.length() - 6);
                String l2 = line.substring(line.length() - 6, line.length() - 3);
                String l1 = line.substring(line.length() - 3);
                System.out.println(
                        standardXXX(Integer.parseInt(l3))
                                + " million "
                                + standardXXX(Integer.parseInt(l2))
                                + " thousand "
                                + standardXXX(Integer.parseInt(l1)));
            }
        }
    }

    //    HJ76 尼科彻斯定理
    void s37() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = br.readLine()) != null) {
            int m = Integer.parseInt(str);
            int power = m * m;
            int start = power - m + 1;
            String res = "";
            for (int i = 0; i < m; i++) {
                res += (start + 2 * i) + "+";
            }
            System.out.println(res.substring(0, res.length() - 1));
        }
    }
}
