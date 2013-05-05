package gofish;

import gofish.config.Config;
import com.google.common.collect.Iterators;
import gofish.model.CardsCollection;
import gofish.model.Player;
import gofish.model.Card;
import gofish.exception.GameStatusException;
import gofish.exception.GameStoppedException;
import gofish.exception.PlayerQueryException;
import gofish.model.Series;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Game engine
 */
public class Game {
    
    final public static int COMPLETE_SERIES_SIZE = 4;
    
    final public static int MIN_NUM_PLAYERS = 3;
        
    final public static int MAX_NUM_PLAYERS = 6;

    final public static int MIN_NUM_CARDS = 28;
    
    /**
     * Used to determine who's the winner
     */
    final private static Comparator<Player> comparator = new PlayerSeriesComparator();
    
    public enum Status {PENDING, STARTED, ENDED};
    
    private Status status = Status.PENDING;
    
    private Config config;
     
    private GUIRenderer renderer;
    
    /**
     * Map containing all cards still available in the game
     * (don't belong to complete series)
     */
    private CardsCollection availableCards = new CardsCollection();
    
    public Game(GUIRenderer renderer, Config config) {
        this.renderer = renderer;
        this.config = config;
    }

    public List<Player> getPlayers() {
        return config.getPlayers();
    }
    
    public void start() {
        // Check status and config
        if (status != Status.PENDING) {
            throw new GameStatusException("Game already started");
        }
        config.validate();
        
        // Start game
        status = Status.STARTED;
        renderer.startGame();
        setAvailableCards();
        
        try {
            // Main game loop
            loop();
            
            // End game
            end();
        } catch (GameStoppedException e) {
            // Game stopped unexpectedly
            status = Status.ENDED;
        }
    }
    
    public void end() {
        if (status != Status.STARTED) {
            throw new GameStatusException("Game not started");
        }
        status = Status.ENDED;
        renderer.endGame(getWinner());
    }
    
    public Set<Card> findCards(String property) {
        return availableCards.getByProperty(property);
    }
    
    private void loop() {
        // Create a temp list to cycle
        List<Player> players = new LinkedList<>(config.getPlayers());
        Iterator<Player> iterator = Iterators.cycle(players);
        while (iterator.hasNext()) {
            Player player = iterator.next();
            
            if (player.canPlay()) {
                playFullTurn(player);
            } else {
                // Player is out of the game
                iterator.remove();
            }
            
            if (players.size() == 1 || !canPlay()) {
                // One player is left or no more series can be assembled
                break;
            }
        }
    }
    
    private boolean canPlay() {
        for (String property : availableCards.properties()) {
            if (availableCards.seriesSize(property) >= COMPLETE_SERIES_SIZE) {
                // There are still cards that can form a series
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return player with most completed series (if there are more than one with
     *         the same number of series, one of them will be chosen arbitrarily)
     */
    private Player getWinner() {
        return Collections.max(config.getPlayers(), comparator);
    }
    
    /**
     * Get all cards in game - collect from players
     */
    private void setAvailableCards() {
        for (Player player : config.getPlayers()) {
            availableCards.addAll(player.getHand());
        }
    }
    
    /**
     * Play a full turn
     * 
     * @param player player who's playing
     */
    private void playFullTurn(Player player) {
        while (playSingleTurn(player)) {
            continue;
        }
    }
    
    /**
     * Play a single turn
     * 
     * @param player player who's playing
     * @return true if the player gets another turn, false otherwise
     */
    private boolean playSingleTurn(Player player) {
        boolean anotherTurn = false;
        
        renderer.playerTurn(player);
        if (config.getForceShowOfSeries()) {
            // Reveal completed series
            renderer.showSeries(player);
        }

        try {
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
                anotherTurn = config.getAllowMutipleRequests();
            } else {
                // Go fish
                renderer.goFish(player, playerAsked);
            }
        } catch (PlayerQueryException e) {
            // Player is out of the game
            CardsCollection hand = player.getHand();
            availableCards.removeAll(hand);
            hand.clear();
        }
        
        return anotherTurn;
    }
    
    private Player.Query getQuery(Player player) throws PlayerQueryException {
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
            CardsCollection hand = player.getHand();
            for (String property : card.getProperties()) {
                if (hand.hasSeries(property)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    
    private void moveCard(Player from, Player to, Card card) {
        from.removeCard(card);
        checkPlayer(from);
        Series series = to.addCard(card);
        renderer.moveCard(from, to, card);
        if (series != null) {
            // Card completed a series
            availableCards.removeAll(series.getCards());
            renderer.seriesCompleted(to, series);
            checkPlayer(to);
        }
    }
    
    private void checkPlayer(Player player) {
        if (!player.canPlay()) {
            // No cards left - player is out
            renderer.playerOut(player);
        }
    }
    
    private static class PlayerSeriesComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            // Compare players by number of completed series
            return p1.getCompleteSeries().size() - p2.getCompleteSeries().size();
        }
    }

}
