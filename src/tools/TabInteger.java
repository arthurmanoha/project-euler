package tools;

import static java.lang.Math.max;

/**
 *
 * @author arthu
 */
public class TabInteger {

    private int[] tab; // Stores chunks of the number, unit chunk is at position zero.
    private int nbBlocks = 1;
    private int nbDigitsPerGroup = 4; // How many digits are kept together when printing
    private int maxCellValue = 10000; //(int) Math.sqrt(Integer.MAX_VALUE);
    private boolean mustSplit = false; // Separate at powers of 1000 or 10000 when printing

    /**
     * Initialize the number with an int
     *
     * @param initialValue
     */
    public TabInteger(int initialValue) {
        initTab(initialValue);
    }

    private void initTab(int initialValue) {
        tab = new int[nbBlocks];
        for (int rank = 0; rank < tab.length; rank++) {
            tab[rank] = 0;
        }
        tab[0] = initialValue;
        carry();
    }

    public void setInitSize(int newNbDigits) {
        nbBlocks = newNbDigits / nbDigitsPerGroup + 1;
        initTab(0);
    }

    /**
     * Initialize the number with another TabInteger.
     *
     * @param n
     */
    public TabInteger(TabInteger n) {
        tab = new int[n.tab.length];
        for (int rank = 0; rank < tab.length; rank++) {
            tab[rank] = n.tab[rank];
        }
    }

    /**
     * Transfer the value to higher-weight positions
     */
    private void carry() {
        for (int k = 0; k < tab.length - 1; k++) {
            if (tab[k] > maxCellValue) {
                tab[k + 1] += tab[k] / maxCellValue;
                tab[k] = tab[k] % maxCellValue;
            }
        }
        if (tab[tab.length - 1] > maxCellValue) {
            expandTab();
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int rank = tab.length - 1; rank >= 0; rank--) {
            s += " ";
            s = addLeadingZeroes(s, tab[rank]);
            s += tab[rank];
        }
        if (mustSplit) {
            s = splitInGroups(s);
        }

        s += " (" + getNbSignificantDigits() + " digits)";

        return s;
    }

    /**
     * Split the string in groups of digits
     *
     * @param s
     * @return
     */
    private String splitInGroups(String s) {
        String result = "";
        for (int rank = s.length() - 1; rank >= 0; rank--) {
            if (((s.length() - rank) - 1) % nbDigitsPerGroup == 0 && (s.length() != rank + 1)) {
                result = "." + result;
            }
            result = s.charAt(rank) + result;
        }
        return result;
    }

    /**
     * Add zeroes to a number at one position in the TabInteger.
     *
     * @param s
     * @param value
     */
    private String addLeadingZeroes(String s, int value) {
        if (value <= 999) {
            s += "0";
        }
        if (value <= 99) {
            s += "0";
        }
        if (value <= 9) {
            s += "0";
        }
        return s;
    }

    /**
     * Add an int to this TabInteger
     *
     * @param b
     * @return
     */
    public TabInteger sum(int b) {
        TabInteger s = new TabInteger(this);
        s.tab[0] += b;
        return s;
    }

    /**
     * Add another TabInteger to this TabInteger
     *
     * @param other
     * @return
     */
    public TabInteger sum(TabInteger other) {
        TabInteger sum = new TabInteger(0);

        int nbDigitsThis = this.getNbSignificantDigits();
        int nbDigitsOther = other.getNbSignificantDigits();
        int nbDigitsSum = max(nbDigitsThis, nbDigitsOther) + 1;
        sum.setInitSize(nbDigitsSum);

        for (int rank = 0; rank < sum.nbBlocks; rank++) {
            if (rank < this.nbBlocks) {
                sum.tab[rank] += this.tab[rank];
            }
            if (rank < other.nbBlocks) {
                sum.tab[rank] += other.tab[rank];
            }
        }
        sum.carry();

        return sum;
    }

    /**
     * Multiply this TabInteger by an integer.
     *
     * @param n
     * @return another TabInt equal to this TabInt multiplied by the specified
     * value.
     */
    public TabInteger mult(int n) {
        TabInteger res = new TabInteger(this);
        for (int rank = 0; rank < tab.length; rank++) {
            res.tab[rank] = this.tab[rank] * n;
        }
        res.carry();
        return res;
    }

    /**
     * Multiply this TabInteger by another TabInteger.
     *
     * @param other
     * @return another TabInteger equal to this TabInt multiplied by the
     * specified TabInteger.
     */
    public TabInteger mult(TabInteger other) {
        int nbDigitsThis = this.getNbSignificantDigits();
        int nbDigitsOther = other.getNbSignificantDigits();
        int maxNbDigitsProduct = nbDigitsThis + nbDigitsOther;

        TabInteger result = new TabInteger(0);
        result.setInitSize(maxNbDigitsProduct);

        for (int i = 0; i < this.tab.length; i++) {
            int a = this.tab[i];
            for (int j = 0; j < other.tab.length; j++) {
                int b = other.tab[j];
                if (i + j < result.tab.length) {
                    result.tab[i + j] += a * b;
                }
            }
            result.carry();
        }

        return result;
    }

    /**
     * Get the number of significant digits, rounded to the upper multiple of
     * group size (e.g. function will return 4 for all of 1, 12, 123 and 1234)
     *
     * @return the number of significant digits
     */
    public int getNbSignificantDigits() {
        int result = tab.length * nbDigitsPerGroup;

        int rank = tab.length;
        // Eliminate empty blocks
        do {
            result -= nbDigitsPerGroup;
            rank--;
        } while (rank >= 0 && tab[rank] == 0);

        if (rank < 0) {
            // Special case for number 0
            return 0;
        } else {
            // All non-zero numbers
            // Block at this rank contains the first non-zero leading digit
            int nbNonzeroDigitsInFirstBlock = (int) Math.log10(tab[rank]) + 1;

            result += nbNonzeroDigitsInFirstBlock;
            return result;
        }
    }

    /**
     * Replace the array with a longer one and copy the previous values.
     */
    private void expandTab() {
        int nbNewCells = 1;

        int[] newTab = new int[tab.length + nbNewCells];
        for (int rank = 0; rank < tab.length; rank++) {
            newTab[rank] = tab[rank];
        }
        tab = newTab;
        carry();
    }

    /**
     * Elevates this TabInteger to the specified integer power.
     *
     * @param pow the power that this TabInteger is being raised to
     * @return this TabInteger raised to the specified power
     */
    public TabInteger power(int pow) {

        if (pow <= 0) {
            return new TabInteger(1);
        }
        TabInteger result = this.mult(power(pow - 1));
        return result;
    }
}
