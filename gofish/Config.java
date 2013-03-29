package gofish;

import gofish.exception.PlayerCollisionException;
import gofish.exception.TooFewCardsException;
import gofish.exception.TooFewPlayersException;
import gofish.exception.TooManyPlayersException;
import gofish.model.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {
    
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
        if (players.size() == Game.MAX_NUM_PLAYERS) {
            throw new TooManyPlayersException();
        }
        players.add(player);
        playerNames.add(name);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void check() {
        if (players.size() < Game.MIN_NUM_PLAYERS) {
            throw new TooFewPlayersException();
        }
        // Count all cards
        int numCards = 0;
        for (Player player : players) {
            numCards += player.numCards();
        }
        if (numCards < Game.MIN_NUM_CARDS) {
            throw new TooFewCardsException();
        }
    }

}
