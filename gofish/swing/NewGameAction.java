package gofish.swing;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

public class NewGameAction extends AbstractAction {
    
    private SwingGame game;
    
    private ConfigDialog configDialog = null;

    public NewGameAction(SwingGame game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Instantiate dialog only when needed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getConfigDialog().setVisible(true);
            }
        });
    }
    
    private ConfigDialog getConfigDialog() {
        if (configDialog == null) {
            configDialog = new ConfigDialog(game);
        }
        return configDialog;
    }

}
