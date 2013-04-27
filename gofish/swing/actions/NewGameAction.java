package gofish.swing.actions;

import gofish.swing.config.ConfigDialog;
import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

public class NewGameAction extends AbstractAction {
    
    private ConfigDialog configDialog;

    public NewGameAction(SwingGame game) {
        super(game, "New game");
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
