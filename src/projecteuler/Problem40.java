package projecteuler;

/**
 *
 * @author arthu
 */
public class Problem40 {

    public Problem40() {
    }

    /**
     * Find the digit at rank n in the number 0.1234567891011121314...
     */
    public void solve() {

        int rank;
        int finalResult = 1;
        for (int exp = 0; exp <= 6; exp++) {
            rank = power(10, exp);
            System.out.println("Rank: " + rank);
            int digit = getChampDigit(rank - 1);
            finalResult = finalResult * digit;
            System.out.println("digit at rank " + rank + " is " + digit + ", product is " + finalResult);
        }
    }

    /**
     * Find which number spans over position 'pos', i.e. which number has a
     * digit written at this position.
     *
     */
    private int getNumberAtRank(int pos, int nbDigits) {
        return (pos + allOnes(nbDigits)) / nbDigits;
    }

    /**
     * Find where the first digit of the number that spans over the given
     * position. Example: number 105 is written at positions 204, 205 and 206;
     * its first digit is '2', located at position 204.
     *
     * @param pos
     * @param nbDigit
     * @return
     */
    private int getFirstDigitRank(int pos, int nbDigit) {
        // Current digit at 'pos' may be the first of its number
        while (getNumberAtRank(pos - 1, nbDigit) == getNumberAtRank(pos, nbDigit)) {
            pos--;
        }
        // At this point, number at 'pos-1' is not the same as number at 'pos',
        // so 'pos' is the rank of the first digit of the current number.
        return pos;
    }

    /**
     * Return the i-th digit of Champernowne's constant
     *
     * @param pos the index if the decimal: 0 -> 1; 1 -> 2
     * @return
     */
    private int getChampDigit(int pos) {

        // Size of the integers represented at position 'pos' in the constant.
        int nbDigits = 1;
        while (pos >= getUpperBound(nbDigits)) {
            nbDigits++;
        }

        int number = getNumberAtRank(pos, nbDigits);

        // The current number has digits at ranks 'firstDigitRank' to 'firstDigitRank+nbDigits-1'
        int firstDigitRank = getFirstDigitRank(pos, nbDigits);

        // Rank of the requested digit within its number, 0 for leading digit.
        int digitRankInNumber = pos - firstDigitRank;

        // The digit we're looking for
        int definitiveDigit = getNthDigit(number, digitRankInNumber);

        return definitiveDigit;

        // Strategy: get the digit for numbers from 1 to 9, then from 10 to 99, ...
        // and then generalize to any value of n.
//        if (pos < 1 * 9) {
//            // One-digit numbers (1 to 9)
//            result = (pos + 1) + ")";
//        } else if (pos < 1 * 9 + 2 * 90) {
//            int number = (pos + 11) / 2;
//            // TODO: extract correct digit from number
//            if (pos == 9) {
//                // This is the '1' of number 10
//                result += '\n';
//            }
//            result += " " + number;
//        } else if (pos < 1 * 9 + 2 * 90 + 3 * 900) {
//            if (pos == 9 + 2 * 90) {
//                // This is the '1' of number 100
//                result += '\n';
//            }
//            int number = (pos + 111) / 3;
//            result += "<" + number + " ";
//        } else {
//            result = "-";
//        }
//        return result;
    }

    /**
     * Return an integer made of 'size' copies of the digit '1'
     *
     * @param size
     * @return a number made of 'size' copies of the digit '1'
     */
    private int allOnes(int size) {
        switch (size) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 10 * allOnes(size - 1) + 1;
        }
    }

    /**
     * Upper bound for the numbers written with 'nbDigits' digits.
     *
     * @param nbDigits the size of the numbers
     * @return the maximum value that can be written in base 10 with that many
     * digits.
     */
    private int getUpperBound(int nbDigits) {
        int bound = 0;
        for (int factor = 1; factor <= nbDigits; factor++) {
            bound += factor * 9 * power(10, factor - 1);
        }
        return bound;
    }

    /**
     * Compute the a to the power exp.
     *
     * @param a
     * @param exp
     * @return
     */
    private int power(int a, int exp) {
        if (exp == 0) {
            return 1;
        }
        return a * power(a, exp - 1);
    }

    /**
     * Extract the n-th digit of a number.
     *
     * @param number the number we want to extract a digit from (ex: 9356)
     * @param rank the rank of the digit we extract, starting at zero (ex: 2)
     * @return the digit located at that position (number 9356 and position 2
     * give digit 5); if rank is incorrect, return -1.
     */
    private int getNthDigit(int number, int rank) {

        // Size of the given number
        int size = (int) (Math.log10(number) + 1);

        if (rank == size - 1) {
            // Return the units digit.
            return number - 10 * (number / 10);
        }

        // Return the n-th digit of the same number truncated of its unit.
        return getNthDigit(number / 10, rank);
    }
}
