package tools;

import java.util.ArrayList;

/**
 * This class tells if a number is prome or not, and keeps a record of all
 * primes it computed previously.
 *
 * @author arthu
 */
public class PrimeTester {

    private ArrayList<Integer> allPrimes = new ArrayList<>();

    int highestPrimeInList = 0;
    int highestIntegerTested = 0;

    /**
     * Create a PrimeTester with no prime pre-computed
     */
    public PrimeTester() {
        allPrimes = new ArrayList<>();
    }

    /**
     * Create a PrimeTester with all numbers tested up to an upper bound
     *
     * @param upperBound the highest integer that will be tested.
     */
    public PrimeTester(int upperBound) {
        this();
        fillPrimeList(upperBound);
    }

    // Make sure we know all primes below n
    public final void fillPrimeList(int n) {
        if (highestIntegerTested < n) {
            System.out.println("Computing primes from " + highestPrimeInList + " to " + n);
            for (int k = highestIntegerTested + 1; k <= n; k++) {
                if (isPrime(k) && !allPrimes.contains(k)) {
                    allPrimes.add(k);
                    highestPrimeInList = k;
                }
                if (1000 * (k / 1000) == k) {
                    int percentage = (100 * k) / n;
                    System.out.println("..." + k + "... " + percentage + "%");
                }
            }
            highestIntegerTested = n;
        }
    }

    // Test if a number is prime, using the list if we can
    private boolean isPrime(int n) {

        if (n > highestPrimeInList) {
            // Test all remaining numbers, including n, and add all the new primes to the list.
            for (int p = highestPrimeInList + 1; p <= n; p++) {
                if (longPrimeTest(p)) {
                    if (!isInList(p, allPrimes)) {
                        allPrimes.add(p);
                        highestPrimeInList = p;
                    }
                }
            }
        }
        return quickPrimeTest(n);
    }

    /**
     * Tell if a number is in the given list
     *
     * @param value
     * @param list
     * @return
     */
    private boolean isInList(int value, ArrayList<Integer> list) {
        return isInListBinarySearch(value, list);
    }

    /**
     * Search for a number in a list in a linear fashion
     *
     * @param value
     * @param list
     * @return
     */
    private boolean isInListSimple(int value, ArrayList<Integer> list) {
        return list.indexOf(value) != -1;
    }

    /**
     * Perform a binary search on an ordered list.
     *
     * @param value
     * @param list
     * @return
     */
    public boolean isInListBinarySearch(int value, ArrayList<Integer> list) {
        int indexLow = 0;
        int indexHigh = list.size() - 1;
        int indexMiddle;
        int step = 0;
        try {
            if (list.get(indexLow) > value) {
                // Lower that first element
                return false;
            } else if (list.get(indexHigh) < value) {
                // Larger that last element
                return false;
            } else if (list.get(indexLow) == value) {
                return true;
            } else if (list.get(indexHigh) == value) {
                return true;
            } else {
                boolean loop = true;

                int maxStep = 5;

                while (loop && step < maxStep) {
                    indexMiddle = (indexLow + indexHigh) / 2;

                    if (list.get(indexMiddle) == value) {
                        return true;
                    } else if (indexHigh - indexLow == 1) {
                        // Low and high bounds joined without finding value
                        return false;
                    } else if (list.get(indexMiddle) > value) {
                        indexHigh = indexMiddle;
                    } else {
                        indexLow = indexMiddle;
                    }
                    step++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
    // Test if a number is prime, with a shortcut: if n is in the list, then it is prime.

    private boolean quickPrimeTest(int n) {
        return isInList(n, allPrimes);
    }

    // Test if a number is prime by testing its divisors.
    private boolean longPrimeTest(int n) {
        boolean isPrime = true;
        if (n < 2) {
            isPrime = false;
        } else if (n <= 3) {
            isPrime = true;
        } else if (2 * (n / 2) == n) {
            isPrime = false;
        } else {
            // QUICK WAY: testing only listed divisors.
            int divRank = 0;
            while (divRank < allPrimes.size()) {
                int div = allPrimes.get(divRank);
                if (div * (n / div) == n) {
                    isPrime = false;
                }
                divRank++;
            }
        }

        return isPrime;
    }

    /**
     * Return a hard copy of the list of primes.
     *
     * @return
     */
    public ArrayList<Integer> getAllPrimes() {
        return allPrimes;
    }
}
