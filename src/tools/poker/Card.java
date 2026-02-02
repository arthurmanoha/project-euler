package tools.poker;

/**
 *
 * @author arthu
 */
public class Card implements Comparable<Card> {

    // Value goes from 2 to 10 and then is 11 for Jacks, 12 for Queens, 13 for Kings and 14 for Aces.
    private int value;

    private CardSuit suit;

    /**
     * Create a card with a value defined by a 2-letter code (examples:
     * "8C", "TS", "KC").
     *
     * @param s
     */
    public Card(String s) {

        switch (s.charAt(0)) {
        case 'T':
            value = 10;
            break;
        case 'J':
            value = 11;
            break;
        case 'Q':
            value = 12;
            break;
        case 'K':
            value = 13;
            break;
        case 'A':
            value = 14;
            break;
        default:
            value = Integer.valueOf(s.substring(0, 1));
        }

        switch (s.charAt(1)) {
        case 'H':
            suit = CardSuit.HEARTS__;
            break;
        case 'D':
            suit = CardSuit.DIAMONDS;
            break;
        case 'S':
            suit = CardSuit.SPADES__;
            break;
        case 'C':
            suit = CardSuit.CLUBS___;
            break;
        default:
        }
    }

    @Override
    public String toString() {
        String res = "";
        if (value != 10) { // use two characters for both a 10 and all other values
            res += " ";
        }
        switch (value) {
        case 11:
            res += "J";
            break;
        case 12:
            res += "Q";
            break;
        case 13:
            res += "K";
            break;
        case 14:
            res += "A";
            break;
        default:
            res += value;
            break;
        }
        res += " " + suit;
        return res;
    }

    @Override
    public int compareTo(Card other) {
        return (this.value - other.value);
    }

    public int getValue() {
        return this.value;
    }

    public CardSuit getSuit() {
        return suit;
    }
}
