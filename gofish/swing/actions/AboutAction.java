package gofish.swing.actions;

import gofish.swing.AboutDialog;
import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class AboutAction extends AbstractAction {
    
    private AboutDialog aboutDialog;

    public AboutAction(SwingGame game) {
        super("About");
        aboutDialog = new AboutDialog(game);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        aboutDialog.setVisible(true);
    }

}
