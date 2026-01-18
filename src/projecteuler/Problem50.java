package projecteuler;

import java.util.ArrayList;
import tools.PrimeTester;

/**
 *
 * @author arthu
 */
public class Problem50 {

    PrimeTester primeTester;

    public Problem50() {
        primeTester = new PrimeTester();
    }

    public void solve() {

//        int n = 41;
//        int n = 953;
        int upperBound = 1000000;
        int recordLength = 0;
        primeTester.fillPrimeList(upperBound);

        int rank = 0;
        int n;
        do {
            n = primeTester.getAllPrimes().get(rank);
            int length = decomposeIntoPrimes(n);
            if (length > recordLength) {
                System.out.println(n + ": " + length);
                recordLength = length;
            }
            rank++;
        } while (n < upperBound && rank < primeTester.getAllPrimes().size());
    }

    private int decomposeIntoPrimes(int n) {
        primeTester.fillPrimeList(n);

        int firstRank = 0; // The rank of the first prime of the sum
        int lastRank = firstRank; // The rank of the last prime of the sum

        final ArrayList<Integer> allPrimes = primeTester.getAllPrimes();

        int firstPrime = allPrimes.get(firstRank);
        int lastPrime = allPrimes.get(lastRank);
        int sum = firstPrime;
        // At startup, the first and last prime considered is TWO, and the sum is 2;

        while (lastRank < allPrimes.size() - 1 && lastPrime < n && sum != n) {

            if (sum < n) {
                // Need to add at one bigger prime
                lastRank++;
                lastPrime = allPrimes.get(lastRank);
                sum += lastPrime;
            }
            if (sum > n) {
                // Need to remove one smaller prime
                sum -= firstPrime;
                firstRank++;
                firstPrime = allPrimes.get(firstRank);
            }

        }
        if (sum == n && firstPrime != lastPrime) {
            // Found solution
//            for (int rank = firstRank; rank <= lastRank; rank++) {
//                System.out.print(allPrimes.get(rank) + " + ");
//            }
//            System.out.println("= " + n);
            int numberOfTerms = lastRank - firstRank + 1;
            return numberOfTerms;
        } else {
            return 0;
        }
    }
}
