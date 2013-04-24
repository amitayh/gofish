package gofish.swing.actions;

import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class RestartGameAction extends AbstractAction {

    private SwingGame game;

    public RestartGameAction(SwingGame game) {
        super("Restart game");
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.restart();
    }

}
