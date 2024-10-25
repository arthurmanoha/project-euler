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
public class Problem26 {

    private static int SIZE_OF_NUMBERS = 10000;

    public Problem26() {
    }

    public void solve() {

        int min = 1;
        int max = 1000;

        int longestCycle = 0;
        int valueForLongestCycle = 0;
        ArrayList<Integer> longestList;

        for (int n = min; n <= max; n++) {
//            System.out.print("n = " + n);
            ArrayList<Integer> currentDecimalInverse = getDecimalInverse(n);
            int length = findCycleLength(currentDecimalInverse);
            if (length != 0) {
                System.out.println("n: " + n + " length " + length + "\t, max length is " + longestCycle + " for value " + valueForLongestCycle);
                if (length > longestCycle) {
//                    System.out.println("----------------------------------------");
                    longestCycle = length;
                    valueForLongestCycle = n;
                    longestList = currentDecimalInverse;

//                    // Display the decimal expansion, but only the five first periods
//                    for (int periodIndex = 0; periodIndex < 5; periodIndex++) {
//                        for (int rank = 0; rank < length; rank++) {
//                            int digit = currentDecimalInverse.get(periodIndex * length + rank);
//                            System.out.print("" + digit);
//                        }
//                        System.out.println("");
//                    }
//                    System.out.println("End of cycle");
//                    System.out.println("Longest cycle: " + longestCycle + " for value " + valueForLongestCycle);
                }
            }
        }

        System.out.println("Longest cycle: " + longestCycle + " for value " + valueForLongestCycle);
        System.out.println("");
    }

    private static ArrayList<Integer> getDecimalInverse(int n) {
        ArrayList list = new ArrayList();

        int numerator = 10;
        int digit = 0;
        int remainder = 0;

        int maxSize = 5 * SIZE_OF_NUMBERS;

        while (list.size() < maxSize) {
            // Compute next digit and add it to the list.
            digit = numerator / n;
            list.add(digit);

            remainder = numerator - digit * n;
            numerator = 10 * remainder;
        }

        return list;
    }

    /**
     * Find the length of the cycle in the array of digits
     *
     * @param tab
     * @return 0 if no cycle detected or exact value (no decimal part), or the
     * actual length of the cycle.
     */
    private static int findCycleLength(ArrayList<Integer> tab) {

        // We assume that no cycle of (number, carry) can be more than 100 terms;
        // and if the list is that long, then it is probably not periodic
        // (or the periodic part starts further into the decimal part.
        // EDIT: in fact bullshit, period is waaaaaay more than 100...
        int maxCandidateLength = SIZE_OF_NUMBERS;

        for (int candidateLength = 1; candidateLength < maxCandidateLength; candidateLength++) {
            if (checkCycle(candidateLength, tab)) {
                return candidateLength;
            }
        }
        // At this point, either the number is not periodic, or we did not have enough digits
        return 0;
    }

    /**
     * Test if the list is nbDigits-periodic from the end on.
     *
     * @param nbDigits the length of the pattern we look for
     * @param list the list at the end of which we look for a repeating pattern
     * @return true when we detect at least several (four) identical cycles of
     * the specified length at the end of the list (must be non-all-zeroes).
     */
    private static boolean checkCycle(int nbDigits, ArrayList<Integer> list) {

        boolean result = true;
        boolean patternIsAllZeroes = true;

        // Check that each of the last <length> digits can be found repeatedly
        for (int rank = list.size() - 1; rank >= list.size() - 1 - nbDigits; rank--) {

            // The digit of the last pattern
            int currentDigit = list.get(rank);
            if (currentDigit != 0) {
                patternIsAllZeroes = false;
            }

            // That same digit must me present in the three patterns to its left.
            for (int patternIndex = 0; patternIndex <= 2; patternIndex++) {
                int repeatedRank = rank - patternIndex * nbDigits;
                if (list.get(repeatedRank) != currentDigit) {
                    // This shows that the pattern is not nbDigit-periodic.
                    result = false;
                }
            }
            // At this point, the tested digit is repeated correctly.
        }
        // At this point, all digits are properly repeated so we do have a periodicity
        return (!patternIsAllZeroes) && result;
    }

}
