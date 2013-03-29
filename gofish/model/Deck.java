package gofish.model;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Standard deck implementation
 */
public class Deck {
    
    final private static String[] RANKS = {
        "2", "3", "4", "5", "6", "7", "8",
        "9", "10", "J", "Q", "K", "A"
    };
    
    final private static String[] SUITS = {
        "Diamonds", "Clubs", "Hearts", "Spades"
    };
    
    private LinkedList<Card> cards = new LinkedList<>();
    
    public Deck() {
        for (String rank : RANKS) {
            for (String suit : SUITS) {
                cards.add(new Card(rank + " of " + suit, rank));
            }
        }
    }
    
    public Card deal() {
        return cards.pop();
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }

}
