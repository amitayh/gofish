package gofish.swing.actions;

import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;

public class RestartGameAction extends AbstractAction {

    public RestartGameAction(SwingGame game) {
        super(game, "Restart game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.restart();
    }

}
