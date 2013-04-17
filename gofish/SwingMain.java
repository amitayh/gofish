package gofish;

import gofish.swing.SwingGame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SwingMain {

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
                Thread gameThread = new Thread(game, "game");
                gameThread.start();
                
                game.setVisible(true);
            }
        });
    }
    
}
