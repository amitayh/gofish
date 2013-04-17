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
    
    private String getCardName(Player playerAsked) {
        SwingGame game = getGame();
        game.setStatusBarText("Choose a card to ask from " + playerAsked.getName());
        Object[] possibilities = {"ham", "spam", "yam"};
        String cardName = (String) JOptionPane.showInputDialog(
            game,
            "Complete the sentence:\n\"Green eggs and...\"",
            "Customized Dialog",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null, //possibilities,
            "ham"
        );
        return cardName;
    }
    
    private Player getLastClicked() {
        return getGame().getGameBoard().getLastClicked().getPlayer();
    }
    
}
