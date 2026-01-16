package projecteuler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        for (int value = 1000; value < 9999; value++) {
            solve(value);
        }
    }

    private void solve(int value) {

        ArrayList<Integer> permutations = getPermutations(value);
        Collections.sort(permutations);

        // Remove duplicates
        Set<Integer> set = new HashSet(permutations);
        permutations.clear();
        permutations.addAll(set);
        permutations.removeIf(n -> !isPrime(n));

        // Return true if two of these prime permutations form an arithmetic sequence
        for (int index0 = 0; index0 < permutations.size(); index0++) {
            int val0 = permutations.get(index0);
            for (int index1 = index0 + 1; index1 < permutations.size(); index1++) {
                int val1 = permutations.get(index1);
                // Search for a third number in the list (already prime) that follows in an arithmetic progression
                int commonDifference = val1 - val0;
                if (commonDifference != 0) {
                    for (int index2 = index1 + 1; index2 < permutations.size(); index2++) {
                        int val2 = permutations.get(index2);
                        if (val2 - val1 == commonDifference) {
                            // Found the final result
                            if (value == val0) {
                                System.out.println("The three values are " + val0 + ", " + val1 + ", " + val2 + ".");
                                System.out.println("Final result: " + val0 + val1 + val2 + ".");
                            }
                        }
                    }
                }
            }
        }
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

    /**
     * Compute all the permutations of a given list
     *
     * @param list
     * @return the meta-list of all permutations
     */
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

    /**
     * Compute the permutations of the digits of a number
     *
     * @param val
     * @return the list of all the integers that we can get through permutation
     * of the digits
     */
    private ArrayList<Integer> getPermutations(int val) {

        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<Integer> listOfDigits = intToList(val);
        ArrayList<ArrayList<Integer>> permutations = getPermutations(listOfDigits);
        for (ArrayList<Integer> singlePermutation : permutations) {
            int numberPermutation = listToInt(singlePermutation);
            result.add(numberPermutation);
        }
        return result;
    }

    /**
     * Convert an integer into the list of its digits in base ten.
     *
     * @param val
     * @return
     */
    private ArrayList<Integer> intToList(int val) {
        ArrayList<Integer> result = new ArrayList<>();
        while (val > 0) {
            int units = val - 10 * (val / 10);
            result.add(0, units);
            val = val / 10;
        }
        return result;
    }

    /**
     * Convert a list of digits into a single integer.
     * The corresponding integer must be within <int> bounds.
     *
     * @param list
     * @return
     */
    private int listToInt(ArrayList<Integer> list) {
        int result = 0;
        while (!list.isEmpty()) {
            result = 10 * result + list.remove(0);
        }
        return result;
    }
}
