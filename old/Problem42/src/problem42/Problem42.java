package problem42;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class Problem42 {

    public static void main(String[] args) {

        ArrayList<String> words = readWords();
        int triWordsCount = 0;

        for (String word : words) {
            int value = getValue(word);
            if (isTriangular(value)) {
//                System.out.println(word + " (" + value + ") is triangular.");
                triWordsCount++;
            }
        }
        System.out.println("There are " + triWordsCount + " triangular words.");
    }

    public static int getTriangle(int rank) {
        return rank * (rank + 1) / 2;
    }

    private static ArrayList<String> readWords() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./words.txt"));

            String text;
            ArrayList<String> finalList = new ArrayList<>();
            while ((text = reader.readLine()) != null) {
                String tab[] = text.split(",");
                for (String word : tab) {
                    finalList.add(word.substring(1, word.length() - 1));
                }
            }
            return finalList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

    private static int getValue(String word) {
        int sum = 0;
        for (char c : word.toCharArray()) {
            // A is 1, S is 19.
            int value = c - 'A' + 1;
            sum += value;
        }
        return sum;
    }

    /**
     * Look for an integer whose triangular value is 'value'.
     *
     * @param value
     * @return
     */
    private static boolean isTriangular(int value) {
        int index = 1;
        while (getTriangle(index) < value) {
            index++;
        }
        return (getTriangle(index) == value);
    }
}
