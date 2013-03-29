package gofish.model;

import gofish.Game;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

abstract public class Player {
    
    public class Query {
        
        private Player playerAsked;
        
        private String cardName;

        public Query(Player playerAsked, String cardName) {
            this.playerAsked = playerAsked;
            this.cardName = cardName;
        }

        public Player getPlayerAsking() {
            return Player.this;
        }

        public Player getPlayerAsked() {
            return playerAsked;
        }

        public String getCardName() {
            return cardName;
        }
        
    }
    
    public enum Type {COMPUTER, HUMAN};
    
    private Type type;
    
    private String name;
    
    private CardsCollection hand = new CardsCollection();
    
    private Collection<Series> completeSeries = new LinkedList<>();

    public Player(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
    public CardsCollection getHand() {
        return hand;
    }
    
    public Collection<Series> getCompleteSeries() {
        return completeSeries;
    }
    
    /**
     * Add card to hand
     * 
     * @param card
     * @return if the added card completed a series, the series is returned, null otherwise
     */
    public Series addCard(Card card) {
        if (!hand.contains(card)) {
            hand.add(card);
            return checkComplete();
        }
        return null;
    }
    
    public void removeCard(Card card) {
        if (!hand.contains(card)) {
            throw new NoSuchElementException();
        }
        hand.remove(card);
    }
    
    public boolean canPlay() {
        return !hand.isEmpty();
    }
    
    public int numCards() {
        return (hand.size() + completeSeries.size());
    }
    
    protected List<Player> otherPlayers(Collection<Player> allPlayers) {
        List<Player> otherPlayers = new ArrayList<>(allPlayers.size());
        for (Player player : allPlayers) {
            if (player != this && player.canPlay()) {
                otherPlayers.add(player);
            }
        }
        return otherPlayers;
    }
    
    private Series checkComplete() {
        for (String property : hand.properties()) {
            if (hand.seriesSize(property) == Game.COMPLETE_SERIES_SIZE) {
                Set<Card> cards = hand.removeSeries(property);
                Series series = new Series(property, cards);
                completeSeries.add(series);
                return series;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Player " + name;
    }
    
    abstract public Player.Query getQuery(Game game);

}
