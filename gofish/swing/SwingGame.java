package gofish.swing;

import gofish.Config;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.player.PlayerPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class SwingGame extends JFrame implements GUIRenderer, Runnable {

    final private static int DEFAULT_WIDTH = 1024;
    
    final private static int DEFAULT_HEIGHT = 768;
    
    private GameBoardPanel gameBoard;
    
    private StatusBarPanel statusBar;
    
    private Config loadedConfig;
    
    private Thread gameThread;

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
    
    public GameBoardPanel getGameBoard() {
        return gameBoard;
    }
    
    public void setStatusBarText(String text) {
        statusBar.setText(text);
    }
    
    public void start(Config config) {
        stop();
        loadedConfig = config;
        gameThread = new Thread(this, "game");
        gameThread.start();
    }
    
    public void stop() {
        if (gameThread != null) {
            gameThread.interrupt();
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameThread = null;
        }
    }
    
    @Override
    public void run() {
        Game game = new Game(this, loadedConfig);
        game.start();
    }

    private void initGameBoard() {
        gameBoard.clear();
        for (Player player : loadedConfig.getPlayers()) {
            gameBoard.addPlayer(player);
        }
        gameBoard.revalidate();
    }

    @Override
    public void startGame() {
        initGameBoard();
        
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
    }

    @Override
    public void invalidQuery(Player.Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerQuery(Player.Query query) {
        statusBar.setText(
            query.getPlayerAsking().getName() + " asked " +
            query.getPlayerAsked().getName() + " for card '" +
            query.getCardName() + "'"
        );
        
        Player playerAsking = query.getPlayerAsking();
        Player playerAsked = query.getPlayerAsked();
        PlayerPanel panel = gameBoard.getPlayerPanel(playerAsking);
        panel.say(playerAsked.getName() + ", do you have " + query.getCardName() + "?");
    }

    @Override
    public void playerOut(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveCard(Player from, Player to, Card card) {
        statusBar.setText(from.getName() + " gives card '" + card.getName() + "' to " + to.getName());
        PlayerPanel fromPanel = gameBoard.getPlayerPanel(from);
        PlayerPanel toPanel = gameBoard.getPlayerPanel(to);
        fromPanel.updateHandPanel();
        toPanel.updateHandPanel();
        fromPanel.say("Yes I do, here you go!");
    }

    @Override
    public void seriesCompleted(Player player, Series series) {
        statusBar.setText(player.getName() + " completed a series");
        PlayerPanel panel = gameBoard.getPlayerPanel(player);
        panel.updateCompletePanel();
    }

    @Override
    public void goFish(Player playerAsking, Player playerAsked) {
        statusBar.setText(playerAsking.getName() + " goes fishing\n");
        PlayerPanel panel = gameBoard.getPlayerPanel(playerAsked);
        panel.say("Go fish!");
    }

    @Override
    public void error(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
