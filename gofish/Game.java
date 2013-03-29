package gofish;

import com.google.common.collect.Iterators;
import gofish.model.CardsCollection;
import gofish.model.Player;
import gofish.model.Card;
import gofish.exception.CardCollisionException;
import gofish.exception.GameStatusException;
import gofish.exception.PlayerCollisionException;
import gofish.exception.TooFewCardsException;
import gofish.exception.TooFewPlayersException;
import gofish.exception.TooManyPlayersException;
import gofish.model.Series;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
    
    public static class Config {
        
        private int minNumPlayers = DEFAULT_MIN_NUM_PLAYERS;
        
        private int maxNumPlayers = DEFAULT_MAX_NUM_PLAYERS;
        
        private int minNumCards = DEFAULT_MIN_NUM_CARDS;
        
        private boolean allowMutipleRequests = true;
        
        private boolean forceShowOfSeries = true;
        
        private List<Player> players = new ArrayList<>();
        
        private Set<String> playerNames = new HashSet<>();

        public void setAllowMutipleRequests(boolean flag) {
            allowMutipleRequests = flag;
        }
        
        public void setForceShowOfSeries(boolean flag) {
            forceShowOfSeries = flag;
        }
        
        public void addPlayer(Player player) {
            String name = player.getName();
            if (playerNames.contains(name)) {
                throw new PlayerCollisionException(name);
            }
            if (players.size() == maxNumPlayers) {
                throw new TooManyPlayersException();
            }
            players.add(player);
            playerNames.add(name);
        }
        
        public int getMinNumPlayers() {
            return minNumPlayers;
        }

        public int getMaxNumPlayers() {
            return maxNumPlayers;
        }
        
        public void check() {
            if (players.size() < minNumPlayers) {
                throw new TooFewPlayersException();
            }
            // Count all cards
            int numCards = 0;
            for (Player player : players) {
                numCards += player.numCards();
            }
            if (numCards < minNumCards) {
                throw new TooFewCardsException();
            }
        }
        
    }
    
    final public static int COMPLETE_SERIES_SIZE = 4;
    
    final private static int DEFAULT_MIN_NUM_PLAYERS = 3;
        
    final private static int DEFAULT_MAX_NUM_PLAYERS = 6;

    final private static int DEFAULT_MIN_NUM_CARDS = 24;
    
    public enum Status {PENDING, STARTED, ENDED};
    
    private Status status = Status.PENDING;
    
    private Config config;
     
    private GuiRenderer renderer;
    
    /**
     * Map containing all cards still available in the game (don't belong to complete series)
     */
    private CardsCollection availableCards = new CardsCollection();
    
    public Game(GuiRenderer renderer, Config config) {
        this.renderer = renderer;
        this.config = config;
    }

    public List<Player> getPlayers() {
        return config.players;
    }
    
    public void start() {
        // Check status config
        if (status != Status.PENDING) {
            throw new GameStatusException("Game already started");
        }
        config.check();
        
        // Start game
        status = Status.STARTED;
        renderer.startGame();
        setAvailableCards();
        
        // Create a temp list to cycle
        List<Player> players = new LinkedList<>(config.players);
        Iterator<Player> iterator = Iterators.cycle(players);
        while (iterator.hasNext()) {
            Player player = iterator.next();
            
            // Play while player has more turns
            boolean continuePlaying = true;
            while (continuePlaying) {
                continuePlaying = playTurn(player);
            }
            
            // Check if player is still in the game
            if (!player.canPlay()) {
                renderer.playerOut(player);
                iterator.remove();
            }
            
            if (players.size() == 1 || gameOver()) {
                break;
            }
        }
        
        // End game
        end();
    }
    
    public void end() {
        if (status != Status.STARTED) {
            throw new GameStatusException("Game not started");
        }
        status = Status.ENDED;
        renderer.endGame(getWinner());
    }
    
    public Set<Card> findCards(String property) {
        return availableCards.getSeries(property);
    }
    
    private boolean gameOver() {
        for (String property : availableCards.properties()) {
            if (availableCards.seriesSize(property) >= COMPLETE_SERIES_SIZE) {
                return false;
            }
        }
        return true;
    }
    
    private Player getWinner() {
        return Collections.max(config.players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getCompleteSeries().size() - p2.getCompleteSeries().size();
            }
        });
    }
    
    /**
     * Get all cards in game - collect from players
     */
    private void setAvailableCards() {
        for (Player player : config.players) {
            for (Card card : player.getHand()) {
                if (availableCards.contains(card)) {
                    throw new CardCollisionException(card.getName());
                }
                availableCards.add(card);
            }
        }
    }
    
    /**
     * Play a single turn
     * 
     * @param player 
     * @return true if the player gets another turn, false otherwise
     */
    private boolean playTurn(Player player) {
        if (player.canPlay()) {
            renderer.playerTurn(player);

            if (config.forceShowOfSeries) {
                // Reveal completed series
                renderer.showSeries(player);
            }

            // Get player's query (ask for specific player and card)
            Player.Query query = getQuery(player);

            // Check if player being asked has the requested card
            Player playerAsked = query.getPlayerAsked();
            String cardName = query.getCardName();
            Card card = playerAsked.getHand().getCard(cardName);
            if (card != null) {
                // Give away card
                moveCard(playerAsked, player, card);
                // Another turn?
                return config.allowMutipleRequests;
            } else {
                // Go fish
                renderer.goFish(player);
            }
        }
        // Continue to next player
        return false;
    }
    
    private Player.Query getQuery(Player player) {
        Player.Query query = player.getQuery(this);
        while (!validateQuery(query)) {
            // Make sure query is valid
            renderer.invalidQuery(query);
            query = player.getQuery(this);
        }
        renderer.playerQuery(query);
        return query;
    }
    
    private boolean validateQuery(Player.Query query) {
        boolean result = false;
        // Check if requested card is in the game
        Card card = availableCards.getCard(query.getCardName());
        if (card != null) {
            // Check that the player is allowed to ask for this card
            Player player = query.getPlayerAsking();
            CardsCollection incomplete = player.getHand();
            for (String property : card.getProperties()) {
                if (incomplete.hasSeries(property)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    
    private void moveCard(Player from, Player to, Card card) {
        renderer.moveCard(from, to, card);
        from.removeCard(card);
        Series series = to.addCard(card);
        if (series != null) {
            // Card completed a series
            for (Card cardInSeries : series.getCards()) {
                availableCards.remove(cardInSeries);
            }
            renderer.seriesCompleted(to, series);
        }
    }

}
