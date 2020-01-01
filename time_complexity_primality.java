import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    private static final String isPrime = "Prime";
    private static final String isNotPrime = "Not prime";

    static String primality(int n) {
        if (n == 2) {
            /* Trivial check for the only even prime. */
            return isPrime;
        } else if (n < 2 || (n & 1) == 0) {
            /* Primes cannot be even or less than 2. */
            return isNotPrime;
        } else {
            /* Assertion: square = factor * factor */
            int factor = 3;
            int square = 9;
            /* Walks over odd factors up to square root
               of the given value. */
            while (square <= n) {
                if (n % factor == 0) {
                    return isNotPrime;
                }
                /* Uses the following equivalence:
                    (x + 2)^2 = x^2 + 4(x + 1) */
                square += (factor + 1) << 2;
                factor += 2;
            }
            /* The given value has no non-trivial factors, 
               and thus is prime. */
            return isPrime;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int p = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int pItr = 0; pItr < p; pItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            String result = primality(n);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
