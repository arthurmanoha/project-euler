package project37;

/**
 *
 * @author arthu
 */
public class Project37 {

    public static void main(String[] args) {
        System.out.println("This is project 37.");

        int nbBiTruncFound = 0;
        int sum = 0;

        int n = 1;
        while (nbBiTruncFound < 11) {
            if (isTruncLeftPrime(n) && isTruncRightPrime(n)) {
                System.out.println(n + " is left- and right-truncatable");
                nbBiTruncFound++;
                sum += n;
            }
            n++;
        }

        System.out.println("The sum is " + sum);
    }

    public static void display(int n) {
        if (n < 1000) {
            System.out.print(" ");
        }
        if (n < 100) {
            System.out.print(" ");
        }
        if (n < 10) {
            System.out.print(" ");
        }
        System.out.print("" + n);
    }

    public static boolean isPrime(int n) {
        if (n < 2) { // 0 and 1
            return false;
        } else if (n == 2) { // 2
            return true;
        } else if (2 * (n / 2) == n) { // every even number except 2
            return false;
        } else { // Test all dividers.
            int div = 3;
            while (div < n) {

                if ((n / div) * div == n) {
                    // div divides n.
                    return false;
                }
                div += 2;
            }
        }
        return true;
    }

    /**
     * Return true if the integer remains prime when we remove the digits from
     * the right (i.e.the units)
     *
     * @param n the integer we test
     * @return true when right truncatable prime.
     */
    public static boolean isTruncRightPrime(int n) {
        if (n < 10) {
            return false;
        }
        while (n > 0) {
            if (!isPrime(n)) {
                // At this point when digits are removed, the number does NOT remain prime.
                return false;
            }
            // Remove the unit digit.
            n = n / 10;
        }
        // At this point, the number was prime at each step and remained prime to its last digit.
        return true;
    }

    /**
     * Return true if the integer remains prime when we remove the digits from
     * the left (i.e.the string-weight digits)
     *
     * @param n the integer we test
     * @return true when left truncatable prime.
     */
    public static boolean isTruncLeftPrime(int n) {
        if (n < 10) {
            return false;
        }
        while (n > 0) {
            if (!isPrime(n)) {
                // At this point when digits are removed, the number does NOT remain prime.
                return false;
            }
            // Remove the first digit.
            n = removeFirstDigit(n);
        }
        // At this point, the number was prime at each step and remained prime to its last digit.
        return true;
    }

    private static int removeFirstDigit(int n) {

        // The unit digit of n:
        int unit = n - 10 * (n / 10);

        if (n < 10) {
            return 0;
        } else {
            return removeFirstDigit(n / 10) * 10 + unit;
        }
    }

}
