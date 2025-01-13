package projecteuler;

import tools.TabInteger;

/**
 *
 * @author arthu
 */
public class Problem48 {

    public Problem48() {
    }

    public void solve() {

        TabInteger result = new TabInteger(0);

        int finalRank = 1000;

        for (int n = 1; n <= finalRank; n++) {
            TabInteger nPowerN = new TabInteger(n).power(n);
            TabInteger buffer = new TabInteger(nPowerN);

            result = result.sum(nPowerN);
            if (n % 10 == 0) {
                System.out.println(n + "");
            }
        }
        System.out.println(result + "");
    }

}
