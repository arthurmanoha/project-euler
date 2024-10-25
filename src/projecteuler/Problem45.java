package projecteuler;

/**
 *
 * @author arthu
 */
public class Problem45 {

    public Problem45() {
    }

    public void solve() {

        long tN = 0;
        long pN = 0;
        long hN = 0;

        long triN = tri(tN);
        long pentaN = penta(pN);
        long hexaN = hexa(hN);

        int step = 0;
        int nbSolutionsFound = 0;
        boolean finish = false;
        do {

            if (triN == pentaN && pentaN == hexaN) {
                // Success
                System.out.println("step " + step + ", " + nbSolutionsFound + "th SOLUTION: tri(" + tN + ") = " + triN
                        + ", penta(" + pN + ") = " + pentaN
                        + ", hexa(" + hN + ") = " + hexaN + ";");
                tN++;
                triN = tri(tN);
                nbSolutionsFound++;
            } else {
                // Not success
                long min = min3(triN, pentaN, hexaN);
                if (triN == min) {
                    tN++;
                    triN = tri(tN);
                } else if (pentaN == min) {
                    pN++;
                    pentaN = penta(pN);
                } else {
                    hN++;
                    hexaN = hexa(hN);
                }
            }
            step++;
        } while (!finish && nbSolutionsFound <= 3);

    }

    public static long tri(long n) {
        return n * (n + 1) / 2;
    }

    public static long penta(long n) {
        return n * (3 * n - 1) / 2;
    }

    public static long hexa(long n) {
        return n * (2 * n - 1);
    }

    /**
     * Tell if the given number is triangular.
     *
     * @param target
     */
    public boolean isTri(int target) {
        int rankMin = 1;
        int rankMax = 2;
        // Find upper bound
        while (tri(rankMax) < target) {
            rankMax = 2 * rankMax;
        }

        // Dichotomy to find the best rank
        int rankMiddle;

        while (rankMax > rankMin + 1) {
            rankMiddle = (rankMin + rankMax) / 2;
            if (tri(rankMiddle) > target) {
                rankMax = rankMiddle;
            } else {
                rankMin = rankMiddle;
            }
        }
        return tri(rankMin) == target || tri(rankMax) == target;
    }

    /**
     * Tell if the given number is pentagonal.
     *
     * @param target
     */
    public boolean isPenta(long target) {
        long rankMin = 1;
        long rankMax = 2;
        // Find upper bound
        while (penta(rankMax) < target) {
            rankMax = 2 * rankMax;
//            System.out.println("rankMax: " + rankMax);
        }
//        System.out.println("Penta: bounds are " + rankMin + " and " + rankMax + ";");
        // Dichotomy to find the best rank
        long rankMiddle;

        while (rankMax > rankMin + 1) {
            rankMiddle = (rankMin + rankMax) / 2;
            if (penta(rankMiddle) > target) {
                rankMax = rankMiddle;
            } else {
                rankMin = rankMiddle;
            }
        }
        return penta(rankMin) == target || penta(rankMax) == target;
    }

    /**
     * Tell if the given number is hexagonal.
     *
     * @param target
     */
    public boolean isHexa(int target) {
        int rankMin = 1;
        int rankMax = 2;
        // Find upper bound
        while (hexa(rankMax) < target) {
            rankMax = 2 * rankMax;
        }

        // Dichotomy to find the best rank
        int rankMiddle;

        while (rankMax > rankMin + 1) {
            rankMiddle = (rankMin + rankMax) / 2;
            if (hexa(rankMiddle) > target) {
                rankMax = rankMiddle;
            } else {
                rankMin = rankMiddle;
            }
        }
        return hexa(rankMin) == target || hexa(rankMax) == target;
    }

    private long min3(long a, long b, long c) {
        return Math.min(Math.min(a, b), c);
    }
}
