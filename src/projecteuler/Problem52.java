package projecteuler;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author arthu
 */
public class Problem52 {

    public Problem52() {
    }

    public void solve() {

        int max = 100000000;

        int multipleMax = 6;

        boolean found = false;

        int n = 1;
        while (!found) {
            found = true;
            for (int multiple = 2; multiple <= multipleMax; multiple++) {
                if (!haveSameDigits(n, multiple * n)) {
                    found = false;
                }
            }
            if (found) {
                for (int multiple = 1; multiple <= multipleMax; multiple++) {
                    System.out.print(multiple * n + " ");
                }
                System.out.println("");
            }
            n++;
        }
    }

    /**
     * Return true if the two parameter have the same digits
     */
    private boolean haveSameDigits(int a, int b) {

        // Test for zero
        if (a == 0 && b == 0) {
            return true;
        }

        ArrayList<Integer> listA = integerToSortedList(a);
        ArrayList<Integer> listB = integerToSortedList(b);

        // Two numbers with a different amount of digits cannot have the same digits
        if (listA.size() != listB.size()) {
            return false;
        }

        // Compare the digits
        for (int index = 0; index < listA.size(); index++) {
            int digitA = listA.get(index);
            int digitB = listB.get(index);
            if (digitA != digitB) {
                // Different digit at this index
                return false;
            }
        }
        return true;
    }

    /**
     * Convert an integer into the list of its digits
     *
     * @param a
     * @return
     */
    private ArrayList<Integer> integerToSortedList(int a) {
        ArrayList<Integer> result = new ArrayList<>();
        while (a > 0) {
            int lastDigit = a - 10 * (a / 10);
            result.add(lastDigit);
            a = a / 10;
        }
        Collections.sort(result);
        return result;
    }
}
