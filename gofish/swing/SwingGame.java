package gofish.swing;

import gofish.Config;
import gofish.GUIRenderer;
import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.player.Computer;
import gofish.swing.player.PlayerView;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

public class SwingGame extends JFrame implements GUIRenderer {
    
    final private static int WINDOW_WIDTH = 800;
    
    final private static int WINDOW_HEIGHT = 600;
    
    private ConfigDialog configDialog;
    
    private AboutDialog aboutDialog;

    public SwingGame() {
        setTitle("GoFish");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Prepare menu bar
        JMenuBar menu = new Menu(this);
        setJMenuBar(menu);
        
        // Prepare content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(2, 3));
        
        // Window size / position
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
    }

    void about() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(this);
        }
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setVisible(true);
    }

    public void configure() {
        final SwingGame game = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (configDialog == null) {
                    configDialog = new ConfigDialog(game);
                }
                configDialog.setVisible(true);
            }
        });
    }
    
    public void init(Config config) {
        Container contentPane = getContentPane();
        
        boolean first = true;
        for (Player player : config.getPlayers()) {
            PlayerView playerView = new PlayerView(player);
            if (first) {
                playerView.isPlaying();
                playerView.say("Hi, I'm " + player.getName() + "!");
                first = false;
            }
            contentPane.add(playerView);
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame(Player winner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerTurn(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showSeries(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void invalidQuery(Player.Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerQuery(Player.Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerOut(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveCard(Player from, Player to, Card card) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void seriesCompleted(Player player, Series series) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goFish(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void error(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
