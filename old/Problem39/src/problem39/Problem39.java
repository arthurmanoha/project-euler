package problem39;

import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Problem39 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

////        System.out.println(isRectangle(24, 45, 51));
//        ArrayList<int[]> list = findSolutions(120);
//        System.out.println("nbSol: " + list.size());
//        for (int[] tab : list) {
////            System.out.println("tab: " + tab);
//            for (int val : tab) {
//                System.out.print(val + " ");
//            }
//            System.out.println("");
//        }
//
        int maxNbSolutions = 0;
        int bestPerimeter = 0;

        for (int perimeter = 1; perimeter <= 1000; perimeter++) {
            int nbSolutions = findNbSolutions(perimeter);

            if (nbSolutions > maxNbSolutions) {
                maxNbSolutions = nbSolutions;
                bestPerimeter = perimeter;
                System.out.println("perimeter " + perimeter + ", " + (nbSolutions / 6) + " solutions");
            }
        }
        System.out.println(bestPerimeter);
    }

    /**
     * Find the number of possible triplets for a given perimeter.
     *
     */
    private static int findNbSolutions(int perimeter) {
        return findSolutions(perimeter).size();
    }

    /**
     * Return true if the given lengths make a pythagorean triplet.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static boolean isRectangle(int a, int b, int c) {
        return (a * a + b * b == c * c
                || b * b + c * c == a * a
                || c * c + a * a == b * b);
    }

    /**
     * Find all the possible triplets for the given perimeter.
     *
     * @param perimeter
     * @return the list of all triplets
     */
    private static ArrayList<int[]> findSolutions(int perimeter) {
        int a = 1, b = 1, c;
        ArrayList<int[]> results = new ArrayList<>();

        while (a <= perimeter) {
            // Find a solution with b and c for the current value of a.
            while (b <= perimeter) {
                // Find a solution with c for the current values of a and b.
                c = perimeter - a - b;
//                System.out.println(a + ", " + b + ", " + c);
                if (c > 0 && isRectangle(a, b, c)) {
//                    addToResults(a, b, c, results);
                    results.add(new int[]{a, b, c});
                }
                b++;
            }
            a++;
            b = 1;
        }
        return results;
    }
//
//    private void addUnique()
//
//    /**
//     * Sort the triplet (a,b,c), add it without doubles to the list.
//     *
//     * @param a
//     * @param b
//     * @param c
//     * @param results
//     */
//    private static void addToResults(int a, int b, int c, ArrayList<int[]> list) {
//
//        // Sort the array
//        if (a <= b && b <= c) {
//            int tab[] = {a, b, c};
//            addUnique(list, tab);
//        }
//    }

}
