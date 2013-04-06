package gofish;

import gofish.swing.SwingGame;
import javax.swing.SwingUtilities;

public class GoFish {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingGame game = new SwingGame();
                game.setVisible(true);
            }
        });
    }
    
}
