package projecteuler;

import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Problem49 {

    private ArrayList<Integer> allPrimes;
    private int maxIntegerTested;

    public Problem49() {
        allPrimes = new ArrayList<>();
        maxIntegerTested = 0;
    }

    public void solve() {
        System.out.println("Problem 49");

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        // Return true if that number has two permutations that are prime and that form an arithmetic sequence
    }

    // Find out whether a number is prime by testing 2 and all even divisors.
    // TODO: test only the prime divisors from the list.
    public boolean isPrimeSlowVersion(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (2 * (n / 2) == n) {
            // Even number but not TWO
            return false;
        }
        for (int div = 3; div <= Math.sqrt(n); div += 2) {
            if (div * (n / div) == n) {
                return false;
            }
        }
        // Prime number
        return true;
    }

    /**
     * Test the primality of a number using a dictionary of all primes below a
     * certain threshold. If the number tested is larger than that threshold, we
     * must update the dictionary.
     *
     * @param n
     * @return
     */
    public boolean isPrime(int n) {
        // Update the list of known primes so that it contains all primes up to n;
        if (n > maxIntegerTested) {
            for (int candidate = maxIntegerTested + 1; candidate <= n; candidate++) {
                if (isPrimeSlowVersion(candidate)) {
                    // We just found a new prime
                    allPrimes.add(candidate);
                }
            }
            maxIntegerTested = n;
        }

        // Now we have a complete list up to n, so we can just look in the list to know if n is prime.
        return (allPrimes.contains(n)); // TODO not done by dichotomy; improvement can be done here
    }

    /**
     * Return a copy of the original list with the specified value inserted at
     * the given rank.
     *
     * @param val
     * @param rank
     * @param list
     * @return
     */
    private ArrayList<Integer> insert(int val, int rank, ArrayList<Integer> list) {
        ArrayList<Integer> copy = new ArrayList<>();
        for (int old : list) {
            copy.add(old);
        }
        copy.add(rank, val);
        return copy;
    }

    private Iterable<ArrayList<Integer>> insertEverywhere(int val, ArrayList<Integer> list) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (list.isEmpty()) {
            ArrayList<Integer> firstList = new ArrayList<>();
            firstList.add(val);
            result.add(firstList);
            return result;
        }

        // If list has at least one element
        // Insert new value at all possible positions
        for (int rank = 0; rank <= list.size(); rank++) {
            result.add(insert(val, rank, list));
        }
        return result;
    }

    private ArrayList<ArrayList<Integer>> insertEverywhereInAllLists(int val, Iterable<ArrayList<Integer>> allLists) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        for (ArrayList<Integer> list : allLists) {
            for (ArrayList<Integer> insertion : insertEverywhere(val, list)) {
                result.add(insertion);
            }
        }
        return result;
    }

    private ArrayList<ArrayList<Integer>> getPermutations(ArrayList<Integer> list) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        if (list.isEmpty()) {
            ArrayList<Integer> firstPermutation = new ArrayList<>();
            result.add(firstPermutation);
        } else if (list.size() == 1) {
            ArrayList<Integer> firstElem = new ArrayList<>();
            firstElem.add(list.get(0));
            result.add(firstElem);
        } else {
            int head = list.get(0);
            ArrayList<Integer> tail = new ArrayList<>(list.subList(1, list.size()));
            result = insertEverywhereInAllLists(head, getPermutations(tail));
        }

        return result;
    }
}
