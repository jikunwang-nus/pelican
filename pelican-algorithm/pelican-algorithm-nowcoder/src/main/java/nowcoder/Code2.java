package nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/12 13:33
 */
public class Code2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            int minutes = Integer.parseInt(line);
            String valueStr = reader.readLine();
            String[] splits = valueStr.split(" ");
            int[] values = new int[minutes];
            int sum = 0;
            for (int i = 0; i < minutes; i++) {
                values[i] = Integer.parseInt(splits[i]);
                sum += values[i];
            }
            Arrays.sort(values);

            int groupCount = minutes;
            boolean success = false;
            while (!success) {
                try {
                    if (sum % groupCount != 0) continue;
                    /*每组得分*/
                    int score = sum / groupCount;
                    if(values[values.length-1]>score)continue;

                } finally {
                    groupCount--;
                }
            }
        }
    }
}
