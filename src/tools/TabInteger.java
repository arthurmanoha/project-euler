package tools;

/**
 *
 * @author arthu
 */
public class TabInteger {

    private int[] tab;
    private int initSize = 4;
    private int printGroupSize = 4; // How many digits are kept together when printing
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
        tab = new int[initSize];
        for (int rank = 0; rank < tab.length; rank++) {
            tab[rank] = 0;
        }
        tab[0] = initialValue;
        carry();
    }

    private void setInitSize(int newSize) {
        initSize = newSize;
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
//        while (s.charAt(0) == '0') {
//            s = s.substring(1);
//        }
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
            if (((s.length() - rank) - 1) % printGroupSize == 0 && (s.length() != rank + 1)) {
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
//        System.out.println("addLeadingZeroes: " + s + "      " + value);
        if (value <= 999) {
            s += "0";
        }
        if (value <= 99) {
            s += "0";
        }
        if (value <= 9) {
            s += "0";
        }
//        System.out.println("after, s: " + s);
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
     * @param p
     * @return
     */
    public TabInteger sum(TabInteger p) {
        TabInteger sum = new TabInteger(this);
        for (int rank = 0; rank < tab.length; rank++) {
            sum.tab[rank] = this.tab[rank] + p.tab[rank];
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
     * @param n
     * @return another TabInteger equal to this TabInt multiplied by the
     * specified TabInteger.
     */
    public TabInteger mult(TabInteger n) {
        int minSize = this.getNbSignificantDigits() + n.getNbSignificantDigits() + 1;
        // TODO
        return this;
    }

    /**
     * Get the number of significant digits, rounded to the upper multiple of
     * group size (e.g. function will return 4 for all of 1, 12, 123 and 1234)
     *
     * @return the number of significant digits
     */
    private int getNbSignificantDigits() {
        int result = tab.length * printGroupSize;

        int rank = tab.length - 1;
        while (rank >= 0 && tab[rank] == 0) {
            result -= printGroupSize;
            rank--;
        }

        return result;
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
}
