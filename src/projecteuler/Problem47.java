package projecteuler;

import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Problem47 {

    int nbPrimalityTests = 0;
    int highestPrimeInList = 0;
    int highestIntegerTested = 0;

    ArrayList<Integer> allPrimes = new ArrayList<>();
    ArrayList<Integer> nbPrimeFactorList = new ArrayList<>();

    public Problem47() {
    }

    public void solve() {

        int max = 1000000;
        int nbOfRanks = 4;

        int n = 0;
        boolean found = false;
        while (!found && n < max) {
            int res = getNbDistinctPrimeFactors(n);
            nbPrimeFactorList.add(res);

            try {
                found = true;
                for (int k = 0; k < nbOfRanks; k++) {
                    if (nbPrimeFactorList.get(n - k) != nbOfRanks) {
                        found = false;
                    }
                }
                if (found) {
                    System.out.println("yolo " + (n - nbOfRanks + 1));
                    for (int k = 0; k < nbOfRanks; k++) {
                        System.out.println(" " + (n - k) + " has " + getNbDistinctPrimeFactors(n - k) + " divisors : "
                                + getAllPrimeFactors(n - k));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
            }

            if (10000 * (n / 10000) == n) {
                System.out.println(n + ": " + res);
            }
            n++;
        }
    }

    // Make sure we know all primes below n
    private void fillPrimeList(int n) {
        if (highestIntegerTested < n) {
            for (int k = highestIntegerTested + 1; k <= n; k++) {
                if (isPrime(k) && !allPrimes.contains(k)) {
                    allPrimes.add(k);
                    highestPrimeInList = k;
                }
            }
            highestIntegerTested = n;
        }
    }

    private String getAllPrimeFactors(int n) {

        fillPrimeList(n);
        String result = "";

        for (int p : allPrimes) {
            if (p * (n / p) == n) {
                result += " - " + p;
            }
        }
        return result;
    }

    private int getNbDistinctPrimeFactors(int n) {

        fillPrimeList(n);

        int nbPrimeFactors = 0;
        for (int p : allPrimes) {
            if (p * (n / p) == n) {
                nbPrimeFactors++;
            }
        }

        return nbPrimeFactors;
    }

    private boolean isInList(int value, ArrayList<Integer> list) {
        return list.indexOf(value) != -1;
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
}
