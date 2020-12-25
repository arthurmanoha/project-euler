/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project41;

/**
 *
 * @author arthu
 */
public class Project41 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        for (int n = 0; n <= 999999999; n++) {
//            if (isPandigital(n)) {
//                System.out.println(n + " is pandigital.");
//            }
//        }
        int n = 987654321;
        boolean found = false;
        while (!found) {
            if (isPandigital(n)) {
                System.out.println(n);
                if (isPrime(n)) {
                    System.out.print(n + " is prime and pandigital.");
                    found = true;
                }
            }
            n--;
        }
        System.out.println("done.");
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            // 1 is not prime
            return false;
        }
        if (n <= 3) {
            // 2 and 3 are prime
            return true;
        }
        if (2 * (n / 2) == n) {
            // n is even
            return false;
        }
        int div = 3;
        while (div < n) {
            if (div * (n / div) == n) {
                // div divides n, so n is not prime.
                return false;
            }
            div += 2;
        }
        // At this point n is prime.
        return true;
    }

    /**
     * Tell if a given number is 1-to-9-pandigital. A number is pandigital if it
     * has exactly once every digit from 1 to 9, and no occurrence of zero.
     *
     * @param n
     * @return
     */
    private static boolean isPandigital(int n) {

        if (n <= 0) {
            return false;
        }

        int nbDigits = (int) (Math.log10(n) + 1);
//        System.out.println("nbDigits: " + nbDigits);

        // The array that counts the occurrences of each digit, including zero.
        int digitsTab[] = new int[nbDigits + 1];

        // Count the digits.
        while (n > 0) {
            // Extract the units.
            int unit = n - 10 * (n / 10);
            if (unit > nbDigits) {
                // A 1-to-k pandigital number may not use any digit greater than k.
//                System.out.println(n + " has digits too great");
                return false;
            }
            digitsTab[unit]++;
            // Barrel roll the next digit into unit position.
            n = n / 10;
        }

        // Check that each digit except zero happens once.
        if (digitsTab[0] != 0) {
//            System.out.println(n + " has a zero");
            return false;
        }
        for (int i = 1; i <= nbDigits; i++) {
            if (digitsTab[i] != 1) {
                // This digit is not exactly present once.
                return false;
            }
        }
        return true;
    }

}
