package gofish.swing.player;

import gofish.Game;
import gofish.exception.PlayerQueryException;
import gofish.model.Player;
import gofish.swing.SwingGame;
import javax.swing.JOptionPane;

public class Human extends AbstractPlayer {

    public Human(String name) {
        super(Type.HUMAN, name);
    }

    @Override
    public Query getQuery(Game game) throws PlayerQueryException {
        Player playerAsked = getPlayerAsked();
        String cardName = getCardName(playerAsked);
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
    
    /*
    private Set<Card> getCardName(Game game) {
        Set<Card> foo = new HashSet<>();
        CardsCollection hand = getHand();
        for (String property : hand.properties()) {
            Set<Card> cards = game.findCards(property);
            if (cards.size() > hand.seriesSize(property)) {
                for (Card card : cards) {
                    if (!hand.contains(card)) {
                        foo.add(card);
                    }
                }
            }
        }
        return foo;
    }
    */
    
    private String getCardName(Player playerAsked) {
        SwingGame game = getGame();
        game.setStatusBarText("Choose a card to ask from " + playerAsked.getName());
        return (String) JOptionPane.showInputDialog(
            game,
            "Enter card's name:",
            "Choose a Card",
            JOptionPane.PLAIN_MESSAGE
        );
    }
    
    private Player getLastClicked() {
        return getGame().getGameBoard().getLastClicked().getPlayer();
    }
    
}
