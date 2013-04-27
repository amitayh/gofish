package gofish.swing.actions;

import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;

public class StopGameAction extends AbstractAction {

    public StopGameAction(SwingGame game) {
        super(game, "Stop game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.stop();
    }
    
}