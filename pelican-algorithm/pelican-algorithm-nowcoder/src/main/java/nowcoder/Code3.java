package nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/12 14:05
 */
public class Code3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splits = line.split(" ");
            int[] values = new int[splits.length];
            for (int i = 0; i < splits.length; i++) {
                values[i] = Integer.parseInt(splits[i]);
            }
            int M = Integer.parseInt(reader.readLine());
            System.out.println(calculate(values, M));
        }
    }

    static int calculate(int[] values, int M) {
        int min = values[0];
        int max = values[values.length - 1];
        if (M <= min) {
            return 1;
        }
        if (M <= max) {
            int sum = 0;
            /*1 1*/
            /*2 M-1*/
            /*3 */
            for (int i = 1; i < M - min; i++) {
                if (i == 1) {
                    sum += 1;
                } else if (i == 2) {
                    sum += M - min;
                } else {
                    for (int j = 0; j < M/i; j++) {
                        sum++;
                    }
                }
            }
            return sum;
        } else {
            return calculate(values, M) * calculate(values, M - max);
        }
    }
}
