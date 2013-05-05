package gofish.config;

import gofish.Game;
import gofish.exception.CardCollisionException;
import gofish.exception.ConfigValidationException;
import gofish.exception.PlayerCollisionException;
import gofish.exception.TooFewCardsException;
import gofish.exception.TooFewPlayersException;
import gofish.exception.TooManyPlayersException;
import gofish.model.Card;
import gofish.model.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config implements Cloneable {
    
    private boolean allowMutipleRequests = true;
    
    private boolean forceShowOfSeries = true;
    
    private List<Player> players = new ArrayList<>(Game.MAX_NUM_PLAYERS);
    
    private Set<String> playerNames = new HashSet<>();

    public void setAllowMutipleRequests(boolean flag) {
        allowMutipleRequests = flag;
    }
    
    public boolean getAllowMutipleRequests() {
        return allowMutipleRequests;
    }

    public void setForceShowOfSeries(boolean flag) {
        forceShowOfSeries = flag;
    }
    
    public boolean getForceShowOfSeries() {
        return forceShowOfSeries;
    }

    public void addPlayer(Player player) {
        String name = player.getName();
        if (playerNames.contains(name)) {
            throw new PlayerCollisionException(name);
        }
        int numPlayers = players.size();
        if (numPlayers == Game.MAX_NUM_PLAYERS) {
            throw new TooManyPlayersException(Game.MAX_NUM_PLAYERS, numPlayers);
        }
        players.add(player);
        playerNames.add(name);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void validate() throws ConfigValidationException {
        int numPlayers = players.size();
        if (numPlayers < Game.MIN_NUM_PLAYERS) {
            throw new TooFewPlayersException(Game.MIN_NUM_PLAYERS, numPlayers);
        }
        // Count cards and check name collisions
        int numCards = 0;
        Set<String> cardNames = new HashSet<>();
        for (Player player : players) {
            for (Card card : player.getHand()) {
                String name = card.getName();
                if (!cardNames.add(name)) {
                    throw new CardCollisionException(name);
                }
            }
            numCards += player.numCards();
        }
        if (numCards < Game.MIN_NUM_CARDS) {
            throw new TooFewCardsException(Game.MIN_NUM_CARDS, numCards);
        }
    }
    
    @Override
    public Config clone() {
        Config clone = new Config();
        
        clone.allowMutipleRequests = allowMutipleRequests;
        clone.forceShowOfSeries = forceShowOfSeries;
        for (Player player : players) {
            clone.addPlayer(player.clone());
        }
        
        return clone;
    }

}
