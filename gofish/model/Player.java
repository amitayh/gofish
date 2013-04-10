package gofish.model;

import gofish.Game;
import gofish.exception.NoOtherPlayersException;
import gofish.exception.PlayerQueryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

abstract public class Player implements Cloneable {
    
    public enum Type {
        COMPUTER("Computer"),
        HUMAN("Human");
        
        private String value;
        
        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
        
    };
    
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
    
    /**
     * @return total number of cards player has (in hand and in completed series)
     */
    public int numCards() {
        int cardsInCompletedSeries = completeSeries.size() * Game.COMPLETE_SERIES_SIZE;
        return (hand.size() + cardsInCompletedSeries);
    }
    
    /**
     * @param allPlayers all players in game
     * @return a list of other players (excluding self) that are still playing
     * @throws NoOtherPlayersException 
     */
    protected List<Player> otherPlayers(Collection<Player> allPlayers) throws NoOtherPlayersException {
        List<Player> otherPlayers = new ArrayList<>(allPlayers.size());
        for (Player player : allPlayers) {
            if (player != this && player.canPlay()) {
                otherPlayers.add(player);
            }
        }
        if (otherPlayers.isEmpty()) {
            throw new NoOtherPlayersException();
        }
        return otherPlayers;
    }
    
    /**
     * Check if there's a complete series in hand
     * 
     * @return the completed series if it exists, null otherwise
     */
    private Series checkComplete() {
        for (String property : hand.properties()) {
            if (hand.seriesSize(property) == Game.COMPLETE_SERIES_SIZE) {
                Set<Card> cards = hand.removeByProperty(property);
                Series series = new Series(property, cards);
                completeSeries.add(series);
                return series;
            }
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player{type=" + type + ", name=" + name + "}";
    }
    
    @Override
    public Player clone() {
        Player clone;
        
        try {
            clone = (Player) super.clone();
            clone.hand = new CardsCollection(hand);
            clone.completeSeries = new LinkedList<>(completeSeries);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        
        return clone;
    }
    
    abstract public Query getQuery(Game game) throws PlayerQueryException;
    
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

}
