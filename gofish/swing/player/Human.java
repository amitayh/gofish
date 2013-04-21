package gofish.swing.player;

import gofish.Game;
import gofish.exception.PlayerQueryException;
import gofish.model.Card;
import gofish.model.CardsCollection;
import gofish.model.Player;
import gofish.swing.SwingGame;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

public class Human extends AbstractPlayer {

    public Human(String name) {
        super(Type.HUMAN, name);
    }

    @Override
    public Query getQuery(Game game) throws PlayerQueryException {
        Player playerAsked = getPlayerAsked();
        List<String> availableCards = getAvailableCards(game);
        String cardName = getCardName(availableCards, playerAsked);
        return new Query(playerAsked, cardName);
    }
    
    private Player getPlayerAsked() {
        SwingGame game = getGame();
        game.setStatusBarText("Click on a player you want to ask a card from");
        Player playerAsked = getLastClicked();
        while (playerAsked == this) {
            game.setStatusBarText("You can't choose yourself! Try again");
            playerAsked = getLastClicked();
        }
        return playerAsked;
    }
    
    private List<String> getAvailableCards(Game game) {
        List<String> availableCards = new LinkedList<>();
        CardsCollection hand = getHand();
        for (String property : hand.properties()) {
            Set<Card> cards = game.findCards(property);
            if (cards.size() > hand.seriesSize(property)) {
                for (Card card : cards) {
                    if (!hand.contains(card)) {
                        availableCards.add(card.getName());
                    }
                }
            }
        }
        return availableCards;
    }
    
    private String getCardName(List<String> availableCards, Player playerAsked) {
        SwingGame game = getGame();
        game.setStatusBarText("Choose a card to ask from " + playerAsked.getName());
        String[] values = availableCards.toArray(new String[availableCards.size()]);
        Object cardName = null;
        while (cardName == null) {
            cardName = JOptionPane.showInputDialog(
                game,                       // Parent
                null,                       // Message
                "Choose a Card",            // Title
                JOptionPane.PLAIN_MESSAGE,  // Message type
                null,                       // Icon
                values,                     // Option values
                null                        // Selected option
            );
        }
        return (String) cardName;
    }
    
    private Player getLastClicked() {
        return getGame().getGameBoard().getLastClicked().getPlayer();
    }
    
}
