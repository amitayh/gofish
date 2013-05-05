package gofish;

import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
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
                Action newGame = game.getNewGameAction();
                newGame.actionPerformed(null);
            }
        });
    }
    
}
