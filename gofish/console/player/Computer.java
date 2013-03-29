package gofish.console.player;

import gofish.Game;
import gofish.model.Card;
import gofish.model.CardsCollection;
import gofish.model.Player;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Computer extends Player {
    
    private static Random randomGenerator = new Random();

    public Computer(String name) {
        super(Type.COMPUTER, name);
    }

    @Override
    public Query getQuery(Game game) {
        List<Player> otherPlayers = otherPlayers(game.getPlayers());
        Player playerAsked = getPlayerAsked(otherPlayers);
        String cardName = getCardName(game);
        return new Query(playerAsked, cardName);
    }
    
    private Player getPlayerAsked(List<Player> players) {
        int numPlayers = players.size();
        if (numPlayers > 0) {
            int randomIndex = randomGenerator.nextInt(numPlayers);
            return players.get(randomIndex);
        }
        return null;
    }
    
    private String getCardName(Game game) {
        CardsCollection hand = getHand();
        for (String property : hand.properties()) {
            Set<Card> cards = game.findCards(property);
            for (Card card : cards) {
                if (!hand.contains(card)) {
                    return card.getName();
                }
            }
        }
        return null;
    }
    
}
