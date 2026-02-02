package projecteuler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import tools.poker.Card;
import tools.poker.Hand;

/**
 *
 * @author arthu
 */
public class Problem54 {

    public Problem54() {
    }

    public void solve() {

        String filename = "src/projecteuler/poker.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;

            int scorePlayer1 = 0;
            int scorePlayer2 = 0;

            int lineIndex = 0;
            int maxLineIndex = -1;

            while ((line = reader.readLine()) != null && (maxLineIndex == -1 || lineIndex < maxLineIndex)) {
                try {
                    Hand hand1 = new Hand();
                    Hand hand2 = new Hand();

                    String tab[] = line.split(" ");

                    for (int index = 0; index < 5; index++) {
                        hand1.add(new Card(tab[index]));
                        hand2.add(new Card(tab[index + 5]));
                    }

                    int comparison = hand1.compare(hand2);
                    if (comparison > 0) {
                        scorePlayer1++;
                    } else if (comparison == 0) {
                    } else {
                        scorePlayer2++;
                    }
                } catch (NumberFormatException e) {
                    // row used for testing
                }
                lineIndex++;
            }
            System.out.println("Final score: " + scorePlayer1 + " to " + scorePlayer2);

        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println("IOException when reading file.");
        }
    }
}
