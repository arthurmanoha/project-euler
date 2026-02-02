package tools.poker;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A hand represents the cards held by a player.
 *
 * @author arthu
 */
public class Hand {

    // The cards are alwas sorted from low value to high value.
    private ArrayList<Card> cards;

    // Values that characterize this hand and help comparing it to another hand.
    private ArrayList<Integer> elementsOfComparison;

    public Hand() {
        cards = new ArrayList<>();
        elementsOfComparison = new ArrayList<>();
    }

    public void add(Card card) {
        if (cards.size() < 5) {
            cards.add(card);
        }
        Collections.sort(cards);
        if (cards.size() == 5) {
            computeElementsOfComparison();
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (Card c : cards) {
            res += c + " ";
        }
        return res;
    }

    /**
     * Determine the combination realized by the cards in this hand.
     *
     * @return
     */
    public Combination getCombination() {
        if (isStraight()) {
            if (isFlush()) {
                return Combination.STRAIGHT_FLUSH;
            } else {
                return Combination.STRAIGHT;
            }
        } else {
            if (isFlush()) {
                return Combination.FLUSH;
            }
        }

        // If no pattern was detected, it is a high card.
        return Combination.HIGH_CARD;
    }

    private boolean hasStraight() {
        for (int index = 0; index < 4; index++) {
            if (cards.get(index + 1).compareTo(cards.get(index)) != 1) {
                // Is not a straight
                return false;
            }
        }
        // Is a straight
        return true;
    }

    /**
     * Test if all cards have the same suit.
     *
     * @return true when all cards have the same suit.
     */
    public boolean hasFlush() {
        for (Card c : cards) {
            if (c.getSuit() != cards.get(0).getSuit()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test if the cards follow each other in value.
     *
     * @return true for a flush, i.e. arithmetic sequence with a common
     * difference of 1.
     *
     */
    public boolean isStraight() {
        return hasStraight() && !hasFlush();
    }

    public boolean isFlush() {
        return hasFlush() && !hasStraight();
    }

    /**
     * Test if the cards form a sequence of the same suit.
     *
     * @return true when it is a straight flush (sequence of the same suit).
     */
    public boolean isStraightFlush() {
        return hasStraight() && hasFlush() && !hasAce();
    }

    /**
     * Test if two cards have the same value, but not three.
     *
     * @return
     */
    public boolean isAPair() {
        return hasAPair() && !hasThreeOfAKind();
    }

    // Tell if a single pair exists without counting a possible three-of-a-kind
    public boolean hasAPair() {
        return getNbPairs() == 1;
    }

    public boolean isTwoPairs() {
        return getNbPairs() == 2;
    }

    public boolean hasThreeOfAKind() {
        // Find a sequence of three same-value cards with the fourth one being different (or they're the last 3 cards in the hand)
        int v0 = cards.get(0).getValue();
        int v1 = cards.get(1).getValue();
        int v2 = cards.get(2).getValue();
        int v3 = cards.get(3).getValue();
        int v4 = cards.get(4).getValue();

        if (v0 == v1 && v1 == v2 && v2 != v3
                || v0 != v1 && v1 == v2 && v2 == v3 & v3 != v4
                || v1 != v2 && v2 == v3 && v3 == v4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Test if three cards have the same value.
     *
     * @return
     */
    public boolean isThreeOfAKind() {
        return hasThreeOfAKind() && !hasAPair();
    }

    private int getNbPairs() {
        int nbPairs = 0;

        // Find a sequence of two same-value cards with the third one being different (or they're the last 2 cards in the hand)
        int v0 = cards.get(0).getValue();
        int v1 = cards.get(1).getValue();
        int v2 = cards.get(2).getValue();
        int v3 = cards.get(3).getValue();
        int v4 = cards.get(4).getValue();

        if (v0 == v1 && v1 != v2) {
            nbPairs++; // v0-v1
        }
        if (v0 != v1 && v1 == v2 && v2 != v3) {
            nbPairs++; // v1-v2
        }
        if (v1 != v2 && v2 == v3 && v3 != v4) {
            nbPairs++; // v2-v3
        }
        if (v2 != v3 && v3 == v4) {
            nbPairs++; // v3-v4
        }

        return nbPairs;
    }

    public boolean isFullHouse() {
        return hasAPair() && hasThreeOfAKind();
    }

    public boolean isFourOfAKind() {
        int v0 = cards.get(0).getValue();
        int v1 = cards.get(1).getValue();
        int v2 = cards.get(2).getValue();
        int v3 = cards.get(3).getValue();
        int v4 = cards.get(4).getValue();
        // Cards are sorted by value; we get four of a kind if we have abcd or bcde, with a!=e
        return (v1 == v2 && v2 == v3 && (v0 == v1 ^ v3 == v4));
    }

    private boolean hasAce() {
        int v0 = cards.get(0).getValue();
        int v1 = cards.get(1).getValue();
        int v2 = cards.get(2).getValue();
        int v3 = cards.get(3).getValue();
        int v4 = cards.get(4).getValue();
        return v0 == 14 || v1 == 14 || v2 == 14 || v3 == 14 || v4 == 14;
    }

    public boolean isRoyalFlush() {
        return hasStraight() && hasFlush() && hasAce();
    }

    private int getHighestCard() {
        return cards.get(4).getValue();
    }

    public String getCategory() {
        switch (getRank()) {
        case 0:
            return "High card";
        case 1:
            return "Pair";
        case 2:
            return "Two pairs";
        case 3:
            return "Three of a kind";
        case 4:
            return "Straight";
        case 5:
            return "Flush";
        case 6:
            return "Full house";
        case 7:
            return "Four of a kind";
        case 8:
            return "Straight flush";
        case 9:
            return "Royal flush";
        default:
            return "no_category";
        }
    }

    public int getRank() {
        int rank = 0; // High card

        if (isRoyalFlush()) {
            rank = 9;
        } else if (isStraightFlush()) {
            rank = 8;
        } else if (isFourOfAKind()) {
            rank = 7;
        } else if (isFullHouse()) {
            rank = 6;
        } else if (isFlush()) {
            rank = 5;
        } else if (isStraight()) {
            rank = 4;
        } else if (isThreeOfAKind()) {
            rank = 3;
        } else if (isTwoPairs()) {
            rank = 2;
        } else if (isAPair()) {
            rank = 1;
        }
        return rank;
    }

//    /**
//     * Compare the hands by looking at each card individually, from highest to
//     * lowest.
//     *
//     * @param other
//     * @return
//     */
//    private int compareUnitCards(Hand other) {
//        int index = 4;
//        int result = 0;
//        while (index >= 0) {
//            int thisValue = this.cards.get(index).getValue();
//            int otherValue = other.cards.get(index).getValue();
//            if (thisValue > otherValue) {
//                result = 1;
//            } else if (thisValue < otherValue) {
//                result = -1;
//            }
//            index--;
//        }
//        System.out.println("Same rank: " + this + (result > 0 ? "> " : "< ") + other);
//        return result;
//    }
    /**
     * Compare two hands.
     *
     * @param other
     * @return -1 if this is smaller than other, 0 if this equals other, +1 if
     * this is larger than other
     */
    public int compare(Hand other) {
        int index = 0;
        while (index < this.elementsOfComparison.size()) {
            int thisElem = this.elementsOfComparison.get(index);
            int otherElem = other.elementsOfComparison.get(index);
            if (thisElem > otherElem) {
                return 1;
            } else if (thisElem < otherElem) {
                return -1;
            }
            index++;
        }
        // If we reach this point, this is a tie.
        return 0;
    }
//    public int compare(Hand other) {
//        int currentRank = this.getRank();
//        int otherRank = other.getRank();
//        int result = 0;
//
//        if (currentRank == otherRank) {
//            int detailedComparison = this.compareCombinations(other, currentRank);
//            if (detailedComparison == 0) {
//                return this.compareUnitCards(other);
//            } else {
//                return detailedComparison;
//            }
//        } else {
//            if (currentRank > otherRank) {
//                result = 1;
//            } else {
//                result = -1;
//            }
//        }
//        return result;
//    }

//    /**
//     * Find the card that exists twice in this hand
//     *
//     * @return
//     */
//    private int evaluatePair() {
//        return evaluatePair(0);
//    }
//
//    private int evaluatePair(int pairRank) {
//        int nbPairsFound = 0;
//        for (int rank1 = 0; rank1 <= 3; rank1++) {
//            for (int rank2 = rank1 + 1; rank2 <= 4; rank2++) {
//                if (cards.get(rank1).getValue() == cards.get(rank2).getValue()) {
//                    // This is the value of the pair
//                    if (nbPairsFound == pairRank) {
//                        return cards.get(rank1).getValue();
//                    } else {
//                        nbPairsFound++;
//                    }
//                }
//            }
//        }
//        return -1; // No pair exists here.
//    }
    // Compare the value of two similar configurations (two pairs, or two three-of-a-kinds, or two full houses)
//    private int compareCombinations(Hand other, int currentRank) {
//        switch (currentRank) {
//        case 0: // High card
//            return this.cards.get(0).getValue() - other.cards.get(0).getValue();
//        case 1: // One pair
//            int thisPair = this.evaluatePair();
//            int otherPair = other.evaluatePair();
//            System.out.println("this pair: " + thisPair + ", other pair: " + otherPair);
//            return thisPair - otherPair;
//        case 2: // Two pairs, compare the card of the highest pair
//            return 10 * this.evaluatePair(1) + this.evaluatePair(0);
//        default:
//            return 0;
//        }
//    }
    /**
     * Compute the different scores that allow us to compare two hands.
     */
    private void computeElementsOfComparison() {

        int rank = getRank();
        elementsOfComparison.add(rank); // First element, common to all hands.

        switch (rank) {
        case 9: // Royal flush
            // No additional information
            break;
        case 8: // Straight Flush
            elementsOfComparison.add(cards.get(0).getValue());
            break;
        case 7: // 4oak
            // Value of quadruple card:
            elementsOfComparison.add(cards.get(0).getValue());
            // Value of the different card, which is either the first ot the last one.
            if (cards.get(0).getValue() != cards.get(1).getValue()) {
                elementsOfComparison.add(cards.get(0).getValue());
            } else {
                elementsOfComparison.add(cards.get(4).getValue());
            }
            break;
        case 6:// Full House
            // card at index 2 is always in the 3oak, pair is either 0-1 or 3-4
            elementsOfComparison.add(cards.get(2).getValue());
            if (cards.get(2).getValue() == cards.get(1).getValue()) {
                // 3oak + pair
                elementsOfComparison.add(cards.get(4).getValue());
            } else {
                // pair + 3oak
                elementsOfComparison.add(cards.get(1).getValue());
            }
            break;
        case 5: // Flush
            for (Card c : cards) {
                elementsOfComparison.add(c.getValue());
            }
            break;
        case 4: // Straight
            elementsOfComparison.add(cards.get(0).getValue());
            break;
        case 3: // 3oak
            // Value of the triple card
            int tripleCardValue = -1;
            for (int index = 0; index < 3; index++) {
                if (cards.get(index).getValue() == cards.get(index + 1).getValue()
                        && cards.get(index + 1).getValue() == cards.get(index + 2).getValue()) {
                    // The 3oak is located between index and (index+2)
                    tripleCardValue = cards.get(index).getValue();
                    elementsOfComparison.add(tripleCardValue);
                }
            }
            // Value of the other 2 cards
            for (int index = 0; index < 4; index++) {
                int singleCardValue = cards.get(index).getValue();
                if (singleCardValue != tripleCardValue) {
                    elementsOfComparison.add(singleCardValue);
                }
            }
            break;
        case 2: // Two pairs
            int firstPairValue = -1;
            int secondPairValue = -1;
            for (int index = 0; index < 4; index++) {
                if (cards.get(index).getValue() == cards.get(index + 1).getValue()) {
                    // Found a pair, first or second
                    if (firstPairValue == -1) {
                        firstPairValue = cards.get(index).getValue();
                    } else {
                        secondPairValue = cards.get(index).getValue();
                    }
                }
            }
            elementsOfComparison.add(firstPairValue);
            elementsOfComparison.add(secondPairValue);
            // Add the last card
            for (int index = 0; index < 4; index++) {
                int singleCardValue = cards.get(index).getValue();
                if (singleCardValue != firstPairValue && singleCardValue != secondPairValue) {
                    // This is the last card
                    elementsOfComparison.add(singleCardValue);
                }
            }
            break;
        case 1: // One pair
            int pairValue = -1;
            // Add the pair value to the elements
            for (int index = 0; index < 4; index++) {
                if (cards.get(index).getValue() == cards.get(index + 1).getValue()) {
                    pairValue = cards.get(index).getValue();
                    elementsOfComparison.add(pairValue);
                }
            }
            // Add all the other cards in decreasing order
            for (int index = 4; index >= 0; index--) {
                if (cards.get(index).getValue() != pairValue) {
                    elementsOfComparison.add(cards.get(index).getValue());
                }
            }
            break;
        case 0: // High card
            // Add all the other cards in decreasing order
            for (int index = 4; index >= 0; index--) {
                elementsOfComparison.add(cards.get(index).getValue());
            }
            break;
        }
    }
}
