import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    static long arrayManipulation(int UNUSUED, int[][] queries) {
        /* A good way to visualize this solution is akin to a histogram,
           where the histogram itself is modelled by recording the difference
           in height wherever this is one.
           
           I.e.: the following mapping schema links indices of the histogram
           to their respective change in height. It is ordered by index, 
           allowing the histogram to be constructed by walking over the entries. */
        SortedMap<Integer, Long> indexToDifference = new TreeMap<>();
        /* This function is used as a portable macro to add heights together. */
        BiFunction<Long, Long, Long> add = (x, y) -> x + y;
        /* Walk through each query. */
        for (int i = 0; i < queries.length; i++) {
            /* Load query data. */
            int firstIndex = queries[i][0]; 
            int lastIndex = queries[i][1];
            long value = queries[i][2];
            /* At this stage, a range of the histogram needs to be extended
               by a given amount. This is done by incrementing the height
               differential at the start of the range, and decrementing it
               at the end, by the given value. */
            indexToDifference.merge(firstIndex, value, add);
            indexToDifference.merge(lastIndex + 1, -value, add);
        }
        /* The maximum height is then computed by building the histogram as follows. */
        long maximum = 0;
        long sum = 0;
        /* Walk over the histogram entries in order of the index, adding each 
           differential to the new height. */
        for (Long value : indexToDifference.values()) {
            sum = add.apply(sum, value);
            if (sum > maximum) {
                maximum = sum;
            }
        }
        /* Return the result. */
        return maximum;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] queries = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }

        long result = arrayManipulation(n, queries);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
