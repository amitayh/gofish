package gofish.swing;

import gofish.Config;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SwingGame extends JFrame implements GUIRenderer {
    
    final private static int DEFAULT_WIDTH = 800;
    
    final private static int DEFAULT_HEIGHT = 600;
    
    private ConfigDialog configDialog;
    
    private AboutDialog aboutDialog;
    
    private GameBoardPanel gameBoard;
    
    private StatusBarPanel statusBar;
    
    private Config loadedConfig;

    public SwingGame() {
        setTitle("GoFish");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Prepare menu bar
        JMenuBar menu = new Menu(this);
        setJMenuBar(menu);
        
        // Prepare content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        gameBoard = new GameBoardPanel();
        contentPane.add(gameBoard, BorderLayout.CENTER);
        
        statusBar = new StatusBarPanel("Ready");
        contentPane.add(statusBar, BorderLayout.PAGE_END);
        
        // Window size / position
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (configDialog == null) {
                    configDialog = new ConfigDialog(SwingGame.this);
                }
                configDialog.setVisible(true);
            }
        });
    }
    
    public void start(Config config) {
        init(config);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game(SwingGame.this, loadedConfig);
                game.start();
            }
        });
    }
    
    private void init(Config config) {
        loadedConfig = config;
        gameBoard.clear();
        for (Player player : config.getPlayers()) {
            gameBoard.addPlayer(player);
        }
        gameBoard.revalidate();
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
        gameBoard.setCurrentPlayer(player);
        statusBar.setText(player.getName() + " is playing");
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
