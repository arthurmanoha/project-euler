package project35;

/**
 *
 * @author arthu
 */
public class Project35 {

    public static void main(String[] args) {
        System.out.println("This is project 35.");

        int maxInt = 1000000;

        int nbCircularPrimes = 0;
        for (int n = 1; n <= maxInt; n++) {
            if (isCircularPrime(n)) {
                System.out.println(n + " is circular prime");
                nbCircularPrimes++;
            }
        }

        System.out.println("There are " + nbCircularPrimes + " below " + maxInt);

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

    public static boolean isCircularPrime(int n) {

        int nbDigits = (int) Math.log10(n) + 1;

        // There are (nbDigits-1) circular permutations of the number, plus the number itself.
        // We must check that all of them are prime.
        for (int permutIndex = 0; permutIndex < nbDigits; permutIndex++) {
            if (!isPrime(permut(n, permutIndex))) {
                // This particular permutation is NOT prime.
                return false;
            }
        }

        return true;
    }

    /**
     * Compute a permutation of the number.
     *
     * @param n permuted number
     * @param permutIndex amount of times we place the unit digit at the first
     * position
     * @return the result of the permutation
     */
    public static int permut(int n, int permutIndex) {
        if (permutIndex == 0) {
            return n;

        } else {

            int unit = n - 10 * (n / 10); // Last digit.
            int rest = n / 10; // All digits but the last.
            int nbDigits = (int) (Math.log10(n)) + 1;

            int permutOnce = unit * power(10, nbDigits - 1) + rest;

            return permut(permutOnce, permutIndex - 1);
        }
    }

    public static int power(int a, int exp) {
        if (exp < 0) {
            return 0;
        } else if (exp == 0) {
            return 1;
        } else {
            return a * power(a, exp - 1);
        }
    }
}
