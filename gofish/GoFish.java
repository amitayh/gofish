package gofish;

import gofish.swing.SwingGame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GoFish {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Fallback to default
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingGame game = new SwingGame();
                game.setVisible(true);
            }
        });
    }
    
}
