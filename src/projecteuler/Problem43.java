/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecteuler;

import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Problem43 {

    public Problem43() {
    }

    public void solve() {
        System.out.println("Solving problem 43");
        long max = 9876543210l;

        ArrayList<Long> allPandigitalNumbers = createAllPandigitalNumbers(9);

        allPandigitalNumbers.sort(null);

        long sumOfPassingPandigitals = 0;

        int nbPan = allPandigitalNumbers.size();
        for (int rank = 0; rank < nbPan; rank++) {
            long number = allPandigitalNumbers.get(rank);
            if (testTriplets(number)) {
                System.out.println(number + " passed the triplets test");
                sumOfPassingPandigitals += number;
            }
        }

        System.out.println("Total sum: " + sumOfPassingPandigitals);
    }

    private boolean isPandigital(long n) {
        // Init the count
        int[] digitsCounts = new int[10];
        for (int i = 0; i < 10; i++) {
            digitsCounts[i] = 0;
        }
        // Count the digits
        while (n > 0) {
            int digit = (int) (n - 10 * (n / 10));
            digitsCounts[digit]++;
            n = n / 10;
        }
        // Check that each digit happens exactly once.
        int i = 0;
        while (i < 10 && digitsCounts[i] == 1) {
            // The digit 'i' exists exactly once, still good.
            i++;
        }
        if (i == 10) {
            // We checked every digit, so the number actually is pandigital.
            return true;
        } else {
            // We found a digit in zero or at least two places, not pandigital.
            return false;
        }
    }

    /**
     * Create a list containing all the 0-to-9 pandigital numbers, each exactly
     * once. The order of the numbers is not specified.
     *
     * @return the list of all 0-to-9 pandigital numbers.
     */
    private ArrayList<Long> createAllPandigitalNumbers(int maxDigit) {
        ArrayList<Long> list = new ArrayList<>();

        for (int digit = maxDigit; digit >= 0; digit--) {
            list = insertDigitEverywhere(list, digit);
        }
        return list;
    }

    /**
     * Create new numbers by adding the digit in all numbers, at all possible
     * positions.
     *
     * @param list
     * @param digit
     */
    private ArrayList<Long> insertDigitEverywhere(ArrayList<Long> list, int digit) {

        ArrayList<Long> newNumbers = new ArrayList<>();

        if (list.isEmpty()) {
            newNumbers.add(Long.valueOf(digit));
        } else {
            for (long number : list) {
                newNumbers.addAll(insertDigitEverywhere(number, digit));
            }
        }
        return newNumbers;
    }

    /**
     * Create new numbers by adding a digit in one single number at all possible
     * positions. No leading zero shall be added, that would not create a new
     * number.
     *
     */
    private ArrayList<Long> insertDigitEverywhere(long number, int digit) {

        ArrayList<Long> newNumbers = new ArrayList<>();

        int numberSize = (int) (Math.log10(number) + 1);
        int minRank;
        if (digit == 0) {
            minRank = 1;
        } else {
            minRank = 0;
        }
        for (int rank = minRank; rank <= numberSize; rank++) {
            newNumbers.add(addDigitAtRank(number, digit, rank));
        }
        return newNumbers;
    }

    /**
     * Extract the n-th digit of a number.
     *
     * @param number the number we want to extract a digit from (ex: 9356)
     * @param rank the rank of the digit we extract, starting at zero (ex: 2)
     * @return the digit located at that position (number 9356 and position 2
     * give digit 5); if rank is incorrect, return -1.
     */
    private int getNthDigit(long number, int rank) {

        // Size of the given number
        int size = (int) (Math.log10(number) + 1);

        if (rank == size - 1) {
            // Return the units digit.
            return (int) (number - 10 * (number / 10));
        }

        // Return the n-th digit of the same number truncated of its unit.
        return getNthDigit(number / 10, rank);
    }

    private long addDigitAtRank(long number, int insertedDigit, int insertionRank) {

        // Digits from rank 0 to rank included are added one position to the left, i.e. with ten times their values.
        // insertedDigit is added at rank insertionRank;
        // digits from rank insertionRank+1 to max are added at the same position, i.e. with on time their value.
        int nbDigits = (int) (Math.log10(number) + 1);

        long result = 0;

        for (int rank = 0; rank < nbDigits; rank++) {

            long powOfTen = (long) Math.pow(10, nbDigits - 1 - rank);

            int digit = getNthDigit(number, rank);
            if (rank < insertionRank) {
                // Add ten times the value
                result += 10 * digit * powOfTen;
            } else {
                // Add one time the value
                result += digit * powOfTen;
            }
        }

        result += insertedDigit * Math.pow(10, nbDigits - insertionRank);
        return result;
    }

    /**
     * Check that the triplets extracted from number are divisible by the first
     * primes.
     *
     * @param number
     * @return
     */
    private boolean testTriplets(long number) {
        for (int rank = 2; rank <= 8; rank++) {
            int triplet = extractTriplet(number, rank);
            int prime = getNthPrime(rank - 1);
//            System.out.println("\t\t" + rank + "-th prime is " + prime);
            if (!divides(prime, triplet)) {
//                System.out.println("\tNumber " + number
//                        + ", triplet " + rank + "(" + triplet + ")"
//                        + " is not divisible by " + prime);
                return false;
            } else {
//                System.out.println("\tNumber " + number
//                        + ", triplet " + rank + "(" + triplet + ")"
//                        + " is divisible by " + prime);
            }
        }
        return true;
    }

    /**
     * Get the value of the number formed by the three digits at positions rank,
     * rank+1 and rank+2 in n.
     *
     */
    private int extractTriplet(long number, int rank) {
        return 100 * getNthDigit(number, rank - 1)
                + 10 * getNthDigit(number, rank)
                + getNthDigit(number, rank + 1);
    }

    /**
     * Get the n-th prime.
     *
     * @param rank value 0, 1, 2, ...
     * @return the n-th prime, values: 2, 3, 5, ...
     */
    private int getNthPrime(int rank) {
        int count = 0;
        int n = 1;
        int nthPrime = 0;
        while (count < rank) {
            n++;
            if (isPrime(n)) {
                count++;
                if (count == rank) {
                    nthPrime = n;
                }
            }
        }
//        System.out.println(rank + "-th prime: " + nthPrime);
        return nthPrime;
    }

    /**
     * Check if a number is prime.
     *
     * @param n
     * @return true when the number is prime, false otherwise.
     */
    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (2 * (n / 2) == n) {
            return false;
        }
        int div = 3;
        while (!divides(div, n) && div < n / 2) {
            div += 2;
        }
        if (div >= n / 2) {
            // Found prime.
            return true;
        }
        // Not prime.
        return false;
    }

    /**
     * Check that div is a divisor of n.
     *
     * @param div
     * @param n
     * @return true when div divides n exactly
     */
    private boolean divides(int div, int n) {
        return (n / div) * div == n;
    }

    private int find(long target, ArrayList<Long> list) {
        int firstIndex = 0;
        int lastIndex = list.size() - 1;
        long firstValue = list.get(firstIndex);
        long lastValue = list.get(lastIndex);
        long middleValue = firstValue;
        int middleIndex = firstIndex;

        while (middleValue != target) {
//            System.out.println(firstValue + "(" + firstIndex
//                    + ") -> " + lastValue + "(" + lastIndex + ") "
//                    + (lastIndex - firstIndex));
            middleIndex = (firstIndex + lastIndex) / 2;
            middleValue = list.get(middleIndex);
            if (middleValue < target) {
                firstIndex = middleIndex;
                firstValue = middleValue;
            } else {
                lastIndex = middleIndex;
                lastValue = middleValue;
            }
        }
        return middleIndex;
    }

}
