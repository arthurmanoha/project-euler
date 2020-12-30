package projecteuler;

/**
 *
 * @author arthu
 */
public class Problem44 {

    public Problem44() {
    }

    public void solve() {

        // We try all the pairs of indices, of increasing sums.
        // Sum 2: values (1,1)
        // Sum 3: values (1,2), (2,1)
        // Sum n: values (1, n-1), (2, (n-2)), ... ((n-1), 1)
        int sumOfIndices = 2;
        boolean finished = false;

        int distance = Integer.MAX_VALUE;

        while (!finished) {

            for (int i = 1; i <= (sumOfIndices - 1) / 2; i++) {
                int j = sumOfIndices - i;

                // Try P(i) and P(j).
                int penta0 = getPenta(i);
                int penta1 = getPenta(j);
                if (sumAndDiffTest(penta0, penta1)) {
                    int sum = penta0 + penta1;
                    int diff = Math.abs(penta0 - penta1);
                    System.out.println("Success for "
                            + i + "->" + getPenta(i) + ", " + j + "->" + getPenta(j)
                            + ": sum is " + sum + " (index " + getPentagonalRank(sum)
                            + "), diff is " + diff + " (index " + getPentagonalRank(diff) + ");");
                    if (diff < distance) {
                        distance = diff;
                        System.out.println("Distance: " + distance);
                    }
                }
            }
            sumOfIndices++;
            if (1000 * (sumOfIndices / 1000) == sumOfIndices) {
                System.out.println("Sum of indices: " + sumOfIndices);
            }
        }

        int index0 = 1020;
        int index1 = 2167;
        System.out.println("P(" + index0 + ") = " + getPenta(index0)
                + "; P(" + index1 + ") = " + getPenta(index1));
    }

    /**
     * Return the n-th pentagonal number
     *
     * @param n
     * @return
     */
    private int getPenta(int n) {
        return n * (3 * n - 1) / 2;
    }

    /**
     * Check if a given number is pentagonal or not.
     *
     * @param n
     * @return
     */
    private boolean isPentagonal(int candidate) {
        return getPentagonalRank(candidate) != -1;
    }

    /**
     * Return
     *
     * @param candidate
     * @return -1 when the candidate is not pentagonal, or the index n of P(n)
     * if the candidate is P(n).
     */
    private int getPentagonalRank(int candidate) {

        // Find the rank such that candidate is the rank-th pentagonal number.
        if (candidate <= 0) {
            return -1;
        }
        int rank = 0;
        while (getPenta(rank) < candidate) {
            rank++;
        }
        if (getPenta(rank) == candidate) {
            return rank;
        } else {
            return -1;

        }
    }

    /**
     * Check that two numbers are pentagonal and that both their sum and
     * difference are pentagonal.
     *
     * @param a
     * @param b
     * @return thru when numbers are pentagonal with pentagonal sum and
     * pentagonal difference.
     */
    private boolean sumAndDiffTest(int a, int b) {
        return (isPentagonal(a) && isPentagonal(b)
                && isPentagonal(a + b)
                && isPentagonal(Math.abs(a - b)));
    }
}
