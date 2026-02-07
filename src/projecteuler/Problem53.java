package projecteuler;

import tools.TabInteger;

/**
 *
 * @author arthu
 */
public class Problem53 {

    public Problem53() {

    }

    public void solve() {

        TabInteger oneMillion = new TabInteger(1000000);
        int total = 0;

        int max = 100;

        int k = 1;
        while (k <= max) {

            int n = k;
            while (n <= max) {

                TabInteger choosekn = choose(k, n);
                System.out.println("choose(" + k + ", " + n + ") = " + choosekn);
                if (choosekn.isGreaterThan(oneMillion)) {
                    // All values of choose(k, n2) with n2>n will be larger than choose(k,n)
                    total += max - n + 1;
                    n = max; // Break the loop
                }
                n++;
            }
            k++;
        }
        System.out.println("total: " + total);
    }

    /**
     * Compute a factorial using regular integers.
     *
     * @param n
     * @return factorial n
     */
    private int factorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /**
     * Compute a binomial coefficient using TabIntegers.
     *
     * @param k
     * @param n
     * @return the value of n choose k
     */
    private TabInteger choose(int k, int n) {

        TabInteger result = TabInteger.factorial(n); // Compute n!

        for (int divB = 1; divB <= k; divB++) { // Compute n! / (k!)
            result = result.divide(divB);
        }

        for (int divC = 1; divC <= (n - k); divC++) { // Compute n! / (k! * (n-k)!)
            result = result.divide(divC);
        }

        return result;
    }
}
