/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg32;

import java.util.ArrayList;

/**
 *
 * @author arthurmanoha
 */
public class Main{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        int a = 1;
        int b = 1;
        int prod;
        int sum = 0;
        ArrayList<Integer> productsList = new ArrayList<>();

        boolean loop = true;

        while(loop){

            if((a / 1000) * 1000 == a){
                System.out.println("a = " + a);
            }

            prod = a * b;
            if(!listContainsValue(productsList, prod)){
                if(isPandigital(a, b, prod)){
                    sum += prod;
                    System.out.println("" + a + ", " + b + ", " + prod + " is pandigital.");
                    productsList.add(prod);
                }
            }

            b++;

            if(tooManyDigits(a, b, prod)){
                b = 1;
                a++;
                prod = 1;
//                if(tooManyDigits(a, b, prod)){
//                Check the amount of digits.
                if((Math.log10(a) + 1) >= 9 || (Math.log10(b) + 1) >= 9){
                    // a and b have grown too large, end of search.
                    loop = false;
                }
            }
        }
        System.out.println("Total sum: " + sum);
        return;
    }

    public static void extractDigits(int a, int[] tab){
//        System.out.println("           extracting digits from " + a);
        while(a > 0){
            // Extract the unit digit
            int digit = a - 10 * (a / 10);
            // Record one more if this digit in the table.
            tab[digit]++;
            a = a / 10;
        }
    }

    /**
     * A triple is pandigital if all digits from 1 to 9 are used once each, and
     * the digit 0 is never used.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static boolean isPandigital(int a, int b, int c){

        int[] digitTab = new int[10];

        extractDigits(a, digitTab);
//        System.out.println("a: " + a);
//        displayTab(digitTab);
//        System.out.println("--------------");
        extractDigits(b, digitTab);
//        System.out.println("b: " + b);
//        displayTab(digitTab);
//        System.out.println("--------------");
        extractDigits(c, digitTab);
//        System.out.println("c: " + c);
//        displayTab(digitTab);
//        System.out.println("--------------");

        if(digitTab[0] != 0){
            // Tab contains a zero; not pandigital.
            return false;
        }
        for(int i = 1; i < 10; i++){ // Do not check 0, start at 1.
//            System.out.println("        checking digit " + i);
            if(digitTab[i] != 1){
                // Not pandigital
                return false;
            }
        }
        // At this point the triple a, b, c is pandigital.
        return true;
    }

    public static void displayTab(int[] tab){
        for(int i = 0; i < tab.length; i++){
            System.out.print(tab[i] + " ");
        }
        System.out.println("");
    }

    /**
     * Returns true if the combined numbers a, b and c have more than 10 digits;
     * in that case, we know that the triplet is not 1 to 10 pandigital.
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static boolean tooManyDigits(int a, int b, int c){
        int nbA = (int) Math.log10(a) + 1;
        int nbB = (int) Math.log10(b) + 1;
        int nbC = (int) Math.log10(c) + 1;
        return (nbA + nbB + nbC >= 10);
    }

    public static boolean listContainsValue(ArrayList<Integer> list, int val){
        boolean result = false;
        for(int i : list){
            if(i == val){
                result = true;
            }
        }
        return result;
    }
}
