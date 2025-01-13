package projecteuler;

import tools.TabInteger;

/**
 *
 * @author arthu
 */
public class Problem48 {

    public Problem48() {
    }

    private long power(long base, long exp, long mod) {
        if (exp == 0) {
            return 1;
        } else {
            long p = power(base, exp - 1, mod);//% mod;
            return base * p;
        }
    }

    private long power(long base, long exp) {
        if (exp == 0) {
            return 1;
        } else {
            return base * power(base, exp - 1);
        }
    }

    public void solve() {

        TabInteger n = new TabInteger(1);
        System.out.println("init: " + n);
        int factor = 2;
        for (int step = 0; step < 121; step++) {
            n = n.mult(factor);
            System.out.println("n: " + n);
        }

//        TabInteger sum = n.sum(1000);
//        System.out.println("n + 1000: \n" + sum);
//        TabInteger p = new TabInteger(9999);
//        System.out.println("p:\n" + p);
//        System.out.println("n + p:\n" + n.sum(p));
//        long sum = 0;
//
//        long mod = power(10, 10);
//        System.out.println("mod: " + mod);
//
//        for (int n = 1; n <= 100; n++) {
//            long pow = power(n, n);
//            long nn = power(n, n, mod);
//            sum += pow;
//            System.out.println("" + n + ", " + +pow + ", " + nn + ", " + sum);
//        }
    }

}
