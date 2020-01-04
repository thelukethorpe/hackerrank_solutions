import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    private static final int MAXIMUM_BRIBES_PER_PERSON = 2;

    static void minimumBribes(int[] queue) {
        /* Integer representing the minimum number of bribes, where the NULL
           value represents an impossible queue. */
        Integer bribes = 0;
        /* Walk through the queue, counting how many people each given person
           has bribed. */
        for (int i = 0; bribes != null && i < queue.length; i++) {
            /* Represents the person initially at that position in the queue,
               and the person who is currently there. */
            int initPerson = i + 1;
            int currPerson = queue[i];
            /* Any given person cannot move forward through the queue more times
               than they have bribes. If they have, then the queue is impossible. */
            if (currPerson - initPerson > MAXIMUM_BRIBES_PER_PERSON) {
                bribes = null;
            } else {
                /* Similarly, the first person in the queue that be bribed will 
                   be their number of bribes ahead of them initially. */
                int firstBribablePerson = Math.max(0, currPerson - MAXIMUM_BRIBES_PER_PERSON);
                /* Walking forward from this first person, the number of times that
                   the current person has been bribed is counted. */
                for (int j = firstBribablePerson; j < i; j++) {
                    int prevPerson = queue[j];
                    if (prevPerson > currPerson) {
                        bribes++;
                    }
                }
            }
        }
        /* Output to console based on the outcome. */
        System.out.println(bribes != null ? "" + bribes : "Too chaotic");
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}
