package gofish;

import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import gofish.swing.actions.NewGameAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;

public class SwingMain {

    public static void main(String[] args) {
        SwingUtils.setSystemLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Open frame
                SwingGame game = new SwingGame();
                game.setVisible(true);
                // Open config dialog
                Action action = new NewGameAction(game);
                action.actionPerformed(null);
            }
        });
    }
    
}
