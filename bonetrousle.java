import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {
    static long[] bonetrousle(long n, long k, int b) {
        /* Initialize array of results. */
        long[] results = new long[b];
        /* Fill up results to obtain the smallest sum. */
        long sum = 0;
        for (int i = 0; i < b; i++) {
            results[i] = b - i;
            sum += results[i];
        }
        /* Walk through the results a second time, and gradually build up values. 
         * If the desired sum has not been acheived by the end of the second pass,
         * then there is no possible valid result. */
        for (int i = 0; i < b && sum <= n; i++) {
            long d = Math.min(n - sum, k - b);
            results[i] += d;
            sum += d;
            if (sum == n) {
                return results;
            }
        }
        return new long[]{ -1 };
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(scanner.nextLine().trim());

        for (int tItr = 0; tItr < t; tItr++) {
            String[] nkb = scanner.nextLine().split(" ");

            long n = Long.parseLong(nkb[0].trim());

            long k = Long.parseLong(nkb[1].trim());

            int b = Integer.parseInt(nkb[2].trim());

            long[] result = bonetrousle(n, k, b);

            for (int resultItr = 0; resultItr < result.length; resultItr++) {
                bufferedWriter.write(String.valueOf(result[resultItr]));

                if (resultItr != result.length - 1) {
                    bufferedWriter.write(" ");
                }
            }

            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }
}
