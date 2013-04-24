package gofish.swing.actions;

import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class StopGameAction extends AbstractAction {
    
    private SwingGame game;

    public StopGameAction(SwingGame game) {
        super("Stop game");
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.stop();
    }
    
}