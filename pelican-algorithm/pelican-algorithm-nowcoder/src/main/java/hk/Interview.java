package hk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/27 11:05
 */
public class Interview {
    static String print(Set<String> set, boolean know) {
        StringBuilder sb = new StringBuilder();
        sb.append("The guests in [");
        for (String s : set) {
            sb.append("'")
                    .append(s)
                    .append("'")
                    .append(",");
        }
        if (know) {
            sb.append("] know each other");
        } else {
            sb.append("] do not know each other");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int numberCount = Integer.parseInt(line);
            Map<String, Set<String>> map = new HashMap<>();
            for (int i = 0; i < numberCount; i++) {
                String s = reader.readLine();
                map.put(s, new HashSet<>());
            }
            int pairCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < pairCount; i++) {
                String s = reader.readLine();
                String[] split = s.split(" ");
                map.get(split[0]).add(split[1]);
                map.get(split[1]).add(split[0]);
            }
            int k = Integer.parseInt(reader.readLine());
            int min = k;
            String minKey = "";
            for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
                Set<String> value = entry.getValue();
                if (value.size() < k) {
                    if (value.size() < min) {
                        min = value.size();
                        minKey = entry.getKey();
                    }
                } else {
                    System.out.println(print(value, true));
                    break;
                }
            }

        }
    }
}
