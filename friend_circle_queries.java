import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    /* Data-structure that implements fast
       lookup and join operations. */
    private static class DisjointSetUnion<E> {
        /* Map that links values to their respective nodes. */
        private final Map<E, Node> graph;

        public DisjointSetUnion() {
            this.graph = new HashMap<>();
        }

        /* Finds the root associated with the given value. */
        private Node find(E e) {
            Node node = graph.get(e);
            /* If no entry for the given value exists, make one. */
            if (node == null) {
                node = new Node();
                graph.put(e, node);
            }
            /* Walk up the tree until the root is found. */
            while (node.parent != null) {
                node = node.parent;
            }
            return node;
        }

        /* Links the sets associated with the given values 
           to form one whole, returning the combined size. */
        public int join(E a, E b) {
            /* Find the roots of each given value. */
            Node nodeA = find(a);
            Node nodeB = find(b);
            /* If A and B do not belong to the same set, join them. */
            if (nodeA != nodeB) {
                nodeA.join(nodeB);
            }
            /* Return the size of the newly formed union. */
            return nodeA.size;
        }

        /* Represents a single entry to the disjoint union. */
        private class Node {
            private Node parent;
            private int size;

            private void join(Node that) {
                this.size += that.size;
                that.parent = this;
            }

            public Node() {
                this.parent = null;
                this.size = 1;
            }
        } 
    }

    static int[] maxCircle(int[][] queries) {
        /* Q represents the number of queries. */
        int q = queries.length;
        /* Array to store the size of the largest social circle
           for each respective query. */
        int[] result = new int[q];
        /* The size of the largest current social circle. */
        int maxCircleSize = 0;
        /* Structure to represent the social circles themselves. */
        DisjointSetUnion<Integer> socialCircles = new DisjointSetUnion<>();
        
        /* Walk through each query and update the social circles accordingly. */
        for (int i = 0; i < q; i++) {
            /* Load data on people to be queried. */
            int a = queries[i][0];
            int b = queries[i][1];
            /* Join up their social circles and retrieve the updated size. */
            int circleSize = socialCircles.join(a, b);
            if (circleSize > maxCircleSize) {
                maxCircleSize = circleSize;
            }
            /* Load new maximum into results. */
            result[i] = maxCircleSize;
        }

        return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[][] queries = new int[q][2];

        for (int i = 0; i < q; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 2; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }

        int[] ans = maxCircle(queries);

        for (int i = 0; i < ans.length; i++) {
            bufferedWriter.write(String.valueOf(ans[i]));

            if (i != ans.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
