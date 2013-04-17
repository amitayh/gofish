package gofish.swing;

import gofish.Config;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.player.AbstractPlayer;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import gofish.swing.player.PlayerPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class SwingGame extends JFrame implements GUIRenderer, Runnable {

    final private static int DEFAULT_WIDTH = 800;
    
    final private static int DEFAULT_HEIGHT = 600;
    
    private GameBoardPanel gameBoard;
    
    private StatusBarPanel statusBar;

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
    
    @Override
    public void run() {
        Config config = getConfig(); // Temp, create from menu
        Game game = new Game(this, config);
        init(config);
        
        game.start();
    }

    private void init(Config config) {
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
    }

    @Override
    public void invalidQuery(Player.Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playerQuery(Player.Query query) {
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

    private Config getConfig() {
        Config config = new Config();
        
        config.setAllowMutipleRequests(true);
        config.setForceShowOfSeries(true);
        
        AbstractPlayer players[] = {
            new Human("Amitay"),
            new Computer("Alice"),
            new Computer("Bob")
        };
        
        dealCards(players);
        for (AbstractPlayer player : players) {
            config.addPlayer(player);
            player.setGame(this);
        }
        
        return config;
    }

    private void dealCards(AbstractPlayer[] players) {
        Deck deck = new Deck();
        deck.shuffle();
        
        int index = 0;
        while (deck.size() > 0) {
            Player player = players[index];
            Card card = deck.deal();
            player.addCard(card);
            index = (index + 1) % players.length;
        }
    }

}
