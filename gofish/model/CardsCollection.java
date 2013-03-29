package gofish.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CardsCollection extends AbstractCollection<Card> {
    
    /**
     * Mapping between a card's name to card object
     */
    private Map<String, Card> cards = new TreeMap<>();
    
    /**
     * Mapping between properties and to a set of matching cards.
     * For example, a collection containing these cards:
     * 
     * - Card #1 [King, Hearts]
     * - Card #2 [King, Spades]
     * 
     * Will be mapped like this:
     * 
     * - King   => [Card #1, Card #2]
     * - Hearts => [Card #1]
     * - Spades => [Card #2]
     */
    private SetMultimap<String, Card> series = HashMultimap.create();
    
    public void addSeries(String property, Set<Card> set) {
        for (Card card : set) {
            cards.put(card.getName(), card);
            series.put(property, card);
        }
    }
    
    public Set<Card> removeSeries(String property) {
        Set<Card> set = series.removeAll(property);
        for (Card card : set) {
            removeCard(card);
        }
        return set;
    }
    
    public Set<String> properties() {
        return series.keySet();
    }

    public int seriesSize(String property) {
        return series.get(property).size();
    }

    public boolean hasSeries(String property) {
        return series.containsKey(property);
    }
    
    public Card getCard(String cardName) {
        return cards.get(cardName);
    }

    public Set<Card> getSeries(String property) {
        return series.get(property);
    }
    
    private boolean containsCard(Card card) {
        return containsCard(card.getName());
    }
    
    private boolean containsCard(String cardName) {
        return cards.containsKey(cardName);
    }
    
    private boolean removeCard(Card card) {
        if (containsCard(card)) {
            cards.remove(card.getName());
            for (String property : card.getProperties()) {
                series.remove(property, card);
            }
            return true;
        }
        return false;
    }
    
    private boolean removeCard(String cardName) {
        return removeCard(cards.get(cardName));
    }
    
    @Override
    public boolean add(Card card) {
        if (!containsCard(card)) {
            cards.put(card.getName(), card);
            for (String property : card.getProperties()) {
                series.put(property, card);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean remove(Object o) {
        if (o instanceof String) {
            return removeCard((String) o);
        } else if (o instanceof Card) {
            return removeCard((Card) o);
        } else {
            throw new ClassCastException();
        }
    }
    
    @Override
    public int size() {
        return cards.size();
    }
    
    @Override
    public Iterator<Card> iterator() {
        return cards.values().iterator();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof String) {
            return containsCard((String) o);
        } else if (o instanceof Card) {
            return containsCard((Card) o);
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public void clear() {
        cards.clear();
        series.clear();
    }

}
