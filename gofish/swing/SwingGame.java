package gofish.swing;

import gofish.swing.player.Computer;
import gofish.swing.player.PlayerView;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class SwingGame extends JFrame {
    
    final private static int WINDOW_WIDTH = 800;
    
    final private static int WINDOW_HEIGHT = 600;

    public SwingGame() {
        init();        
    }

    private void init() {
        setTitle("GoFish");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JMenuBar menu = new Menu(this);
        setJMenuBar(menu);
        
        JPanel panel = new JPanel(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            Computer player = new Computer("Player " + (i + 1));
            PlayerView playerView = new PlayerView(player);
            if (i == 0) {
                playerView.isPlaying();
                playerView.say("Hi, I'm " + player.getName() + "!");
            }
            panel.add(playerView);
        }
        add(panel);
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    void about() {
        AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setVisible(true);
    }

    public void configure() {
        ConfigDialog configDialog = new ConfigDialog(this);
        configDialog.setVisible(true);
    }

}
