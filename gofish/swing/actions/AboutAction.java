package gofish.swing.actions;

import gofish.swing.AboutDialog;
import gofish.swing.SwingGame;
import java.awt.event.ActionEvent;

public class AboutAction extends AbstractAction {
    
    private AboutDialog aboutDialog;

    public AboutAction(SwingGame game) {
        super(game, "About");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getAboutDialog().setVisible(true);
    }
    
    private AboutDialog getAboutDialog() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(game);
        }
        return aboutDialog;
    }

}
