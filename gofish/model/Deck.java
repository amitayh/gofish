package gofish.model;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Standard deck implementation
 */
public class Deck {
    
    final private static String[] RANKS = {
        "2", "3", "4", "5", "6", "7", "8",
        "9", "10", "j", "q", "k", "a"
    };
    
    final private static String[] SUITS = {
        "d", "c", "h", "s"
    };
    
    private LinkedList<Card> cards = new LinkedList<>();
    
    public Deck() {
        for (String rank : RANKS) {
            for (String suit : SUITS) {
                String name = rank + suit;
                cards.add(new Card(name, rank));
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
