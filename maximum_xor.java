import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    /* Data-structure that enables a fast
       maximum lookup operation. */
    private static class XORTree {
        private static final int DEFAULT_DEPTH = 32; 
        private final InternalNode root;

        public XORTree(int[] values) {
            this.root = new InternalNode(DEFAULT_DEPTH);
            for (int value : values) {
                root.add(value);
            }
        }

        public int max(int value) {
            return root.findLargestXOR(value);
        }

        private interface Node {
            void add(int value);
            int findLargestXOR(int value);
        }

        private class InternalNode implements Node {
            private final int depth;
            private final int bit;
            private Node left, right;

            public InternalNode(int depth) {
                this.depth = depth;
                this.bit = 1 << (depth - 1);
            }

            @Override
            public void add(int value) {
                Node next = depth > 1 ? new InternalNode(depth - 1) : new LeafNode(value);
                if (this.leansLeft(value)) {
                    if (left == null) {
                        left = next;
                    }
                    next = left;
                } else {
                    if (right == null) {
                        right = next;
                    }
                    next = right;
                }
                next.add(value);
            }

            @Override
            public int findLargestXOR(int value) {
                if ((!this.leansLeft(value) && left != null) || right == null) {
                    return left.findLargestXOR(value);
                } else {
                    return right.findLargestXOR(value);
                }
            }

            private boolean hasBit(int value) {
                return (value & bit) > 0;
            }

            private boolean leansLeft(int value) {
                return !hasBit(value);
            }
        }

        private class LeafNode implements Node {
            private final int value;

            public LeafNode(int value) {
                this.value = value;
            }

            @Override
            public void add(int UNUSED) {
                /* Do Nothing... */
            }

            @Override
            public int findLargestXOR(int value) {
                return this.value ^ value;
            }
        }
    }

    static int[] maxXor(int[] arr, int[] queries) {
        int n = queries.length;
        int[] results = new int[n];
        XORTree maxFinder = new XORTree(arr);

        for (int i = 0; i < n; i++) {
            results[i] = maxFinder.max(queries[i]);
        }

        return results;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] queries = new int[m];

        for (int i = 0; i < m; i++) {
            int queriesItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            queries[i] = queriesItem;
        }

        int[] result = maxXor(arr, queries);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
