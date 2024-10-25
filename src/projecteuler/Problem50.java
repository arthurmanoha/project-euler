package projecteuler;

/**
 *
 * @author arthu
 */
public class Problem47 {

    public Problem47() {
    }

    public void solve() {
        // Find the first 4 consecutive numbers to have four distinct prime factors
        int nbIntegers = 4;

        int n = 1;

        boolean found = false;
        while (!found) {

            int nbDistinctFactors[] = getDistinctPrimeFactors(n, nbIntegers);

//            // Display values:
            if (1000 * (n / 1000) == n) {
                for (int i = 0; i < nbDistinctFactors.length; i++) {
                    System.out.println((n + i) + ": " + nbDistinctFactors[i] + " distinct factors.");
                }
                System.out.println("");
            }

            // If the numbers of distinct prime factors are non-zero and all the same,
            // then we found it.
            found = true;
            for (int pos = 0; pos < nbIntegers - 1; pos++) {
                // Check that both number have the requested amount of distinct prime factors.
                if (nbDistinctFactors[pos] != nbIntegers
                        || nbDistinctFactors[pos + 1] != nbIntegers) {
                    // At least one value does not have the right amount of distinct prime factors.
                    found = false;
                }
            }
            if (found) {
                System.out.print(nbIntegers + " distinct prime factors for ");
                for (int value = n; value < n + nbIntegers; value++) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }

            n++;
        }
    }

    /**
     * Return the list of the number of factors for n and the following
     * integers.
     *
     * @param firstValue
     * @param nbIntegers
     * @return how many prime factors the number has, other than itself (or 0 if
     * n is prime).
     */
    private int[] getDistinctPrimeFactors(int firstValue, int nbIntegers) {

        int nbFactors[] = new int[nbIntegers];
        for (int n = firstValue; n < firstValue + nbIntegers; n++) {
            nbFactors[n - firstValue] = getNbDistinctPrimeFactors(n);
        }
        return nbFactors;
    }

    /**
     * Get the number of distinct factors of a given integer.
     *
     * @param n an integer
     * @return how many distinct factors n has.
     */
    private int getNbDistinctPrimeFactors(int n) {
        int nbPrimeFactors = 0;

        int div = 2;
        while (div < n) {
            if (div * (n / div) == n) {
                // div is another prime factor of n
                nbPrimeFactors++;
            }
            div = getNextPrime(div);
        }
        return nbPrimeFactors;
    }

    /**
     * Get the smallest prime strictly greater than n
     *
     * @param n
     * @return
     */
    private int getNextPrime(int n) {
        int candidate = n + 1;
        while (!isPrime(candidate)) {
            candidate++;
        }
        return candidate;
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (2 * (n / 2) == n) {
            return false;
        }
        int div = 3;
        do {
            if (div * (n / div) == n) {
                return false;
            }
            div++;
        } while (div <= n / 2);
        return true;
    }
}
