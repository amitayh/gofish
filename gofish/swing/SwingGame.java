package gofish.swing;

import gofish.Config;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.player.PlayerView;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SwingGame extends JFrame implements GUIRenderer {
    
    final private static int WINDOW_WIDTH = 800;
    
    final private static int WINDOW_HEIGHT = 600;
    
    private ConfigDialog configDialog;
    
    private AboutDialog aboutDialog;
    
    private Map<Player, PlayerView> views = new HashMap<>();
    
    private PlayerView current = null;

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
    
    public void start(Config config) {
        init(config);
        
        Game game = new Game(this, config);
        game.start();
    }
    
    private void init(Config config) {
        Container contentPane = getContentPane();
        empty(contentPane);
        for (Player player : config.getPlayers()) {
            PlayerView view = new PlayerView(player);
            views.put(player, view);
            contentPane.add(view);
        }
        contentPane.revalidate();
    }
    
    private void empty(Container container) {
        for (Component component : container.getComponents()) {
            container.remove(component);
        }
    }

    @Override
    public void startGame() {
        String message = "Game is starting!";
        String title = "GoFish";
        int type = JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    @Override
    public void endGame(Player winner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerTurn(Player player) {
        if (current != null) {
            current.isNotPlaying();
        }
        
        PlayerView view = views.get(player);
        view.isPlaying();
        current = view;
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
