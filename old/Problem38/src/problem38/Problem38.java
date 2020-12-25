package problem38;

/**
 *
 * @author arthu
 */
public class Problem38 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        System.out.println(decomp(192384576));
        int n = 999999999;
        boolean found = false;

        while (!found) {

            // See if n is pandigital
            if (isPandigital(n)) {
//                System.out.println(n + " is the " + nbPandigitalFound + "th pandigital.");
                // Try to decompose n as a|2a|3a|4a...
                if (decomp(n)) {
                    System.out.println(n + ": is decomposable");
                    found = true;
                }
            }
            // Go to the next number.
            n--;
        }
    }

    /**
     * Tell if a given number is 1-to-9-pandigital. A number is pandigital if it
     * has exactly once every digit from 1 to 9, and no occurrence of zero.
     *
     * @param n
     * @return
     */
    private static boolean isPandigital(int n) {

        // The array that counts the occurrences of each digit.
        int digitsTab[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Count the digits.
        while (n > 0) {
            // Extract the units.
            int unit = n - 10 * (n / 10);
            digitsTab[unit]++;
            // Barrel roll the next digit into unit position.
            n = n / 10;
        }

        // Check that each digit except zero happens once.
        if (digitsTab[0] != 0) {
            return false;
        }
        for (int i = 1; i <= 9; i++) {
            if (digitsTab[i] != 1) {
                // This digit is not exactly present once.
                return false;
            }
        }
        return true;
    }

    private static int nbDigits(int n) {
        if (n <= 0) {
            return 1;
        }
        return (int) (Math.log10(n) + 1);
    }

    private static int power(int a, int exp) {
        if (exp <= 0) {
            return 1;
        }
        return a * power(a, exp - 1);
    }

    /**
     * Try to decompose n as the concatenation of a, 2a, 3a, ...
     *
     * @param n
     */
    private static boolean decomp(int n) {

        int base; // The value that gets multiplied and concatenated.
        int sizeOfBase = 1; // The amount of digits we select to create the base.

        int sizeOfN = nbDigits(n);

        while (sizeOfBase < sizeOfN) {

            // Extract the base, which is the 'sizeOfBase' first digits of n.
            int pow10 = power(10, sizeOfN - sizeOfBase);
            base = n / pow10;
//            System.out.println("base: " + base);

            // See if the base concatenated to its multiples can reach n.
            int multiple = 1;
            int concatenation = 0;
            while (concatenation < n && concatenation >= 0) {
                concatenation = concat(concatenation, base * multiple);
                multiple++;
                if (concatenation >= 0) {
//                    System.out.println("n: " + n + ", concatenation: " + concatenation);
                    if (concatenation == n) {
//                        System.out.println("        " + n + ", " + concatenation + " : found !");
                        System.out.println(n + ", base: " + base);
                        return true;
                    }
                }
            }

            sizeOfBase++;
//            System.out.println("");
        }

        return false;
    }

    /**
     * Concatenate two numbers. ex: 1234, 5678 -> 12345678
     *
     * @param a
     * @param b
     * @return
     */
    private static int concat(int a, int b) {
        int pow10 = power(10, nbDigits(b));
        return a * pow10 + b;
    }
}
