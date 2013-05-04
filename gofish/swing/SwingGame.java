package gofish.swing;

import gofish.exception.InvalidQueryException;
import gofish.config.Config;
import gofish.GUIRenderer;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.actions.NewGameAction;
import gofish.swing.actions.RestartGameAction;
import gofish.swing.actions.StopGameAction;
import gofish.swing.player.PlayerPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

public class SwingGame extends JFrame implements GUIRenderer, Runnable {

    final private static int DEFAULT_WIDTH = 1200;
    
    final private static int DEFAULT_HEIGHT = 800;
    
    final private static String READY_TEXT = "Ready";
    
    private GameBoardPanel gameBoard;
    
    private JLabel statusBar;
    
    private Config loadedConfig;
    
    private Config previousConfig;
    
    private Thread gameThread;
    
    private Action newGameAction = new NewGameAction(this);
    
    private Action restartGameAction = new RestartGameAction(this);
    
    private Action stopGameAction = new StopGameAction(this);

    public SwingGame() {
        setTitle("GoFish");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Prepare menu bar
        JMenuBar menu = new Menu(this);
        setJMenuBar(menu);
        
        // Prepare content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JToolBar toolBar = new ToolBar(this);
        contentPane.add(toolBar, BorderLayout.PAGE_START);
        
        gameBoard = new GameBoardPanel();
        contentPane.add(gameBoard, BorderLayout.CENTER);
        
        statusBar = new JLabel(READY_TEXT);
        statusBar.setBorder(new EtchedBorder());
        contentPane.add(statusBar, BorderLayout.PAGE_END);
        
        // Window size / position
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
    }
    
    public GameBoardPanel getGameBoard() {
        return gameBoard;
    }
    
    public void setMessage(String text) {
        statusBar.setForeground(null);
        statusBar.setText(text);
    }
    
    public void setErrorMessage(String text) {
        statusBar.setForeground(Color.RED);
        statusBar.setText(text);
    }
    
    public void start(Config config) {
        stop();
        if (config != null) {
            loadedConfig = config;
            previousConfig = config.clone();
            gameThread = new Thread(this, "game");
            gameThread.setDaemon(true);
            gameThread.start();
        }
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
        gameBoard.clear();
        setMessage(READY_TEXT);
    }
    
    public void restart() {
        start(previousConfig);
    }
    
    public Action getNewGameAction() {
        return newGameAction;
    }

    public Action getRestartGameAction() {
        return restartGameAction;
    }

    public Action getStopGameAction() {
        return stopGameAction;
    }
    
    @Override
    public void run() {
        try {
            Game game = new Game(this, loadedConfig);
            game.start();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void startGame() {
        initGameBoard();
        showInfoDialog("Game is starting!");
    }

    @Override
    public void endGame(Player winner) {
        showInfoDialog("Game ended! Winner is " + winner.getName());
    }

    @Override
    public void playerTurn(Player player) {
        gameBoard.setCurrentPlayer(player);
    }

    @Override
    public void showSeries(Player player) {
        // Not needed with Swing UI
    }

    @Override
    public void invalidQuery(Player.Query query) {
        // This shouldn't happen with Swing UI
        throw new InvalidQueryException(query);
    }

    @Override
    public void playerQuery(Player.Query query) {
        setMessage(
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
        PlayerPanel panel = gameBoard.getPlayerPanel(player);
        panel.playerOut();
    }

    @Override
    public void moveCard(Player from, Player to, Card card) {
        setMessage(from.getName() + " gives card '" + card.getName() + "' to " + to.getName());
        PlayerPanel fromPanel = gameBoard.getPlayerPanel(from);
        PlayerPanel toPanel = gameBoard.getPlayerPanel(to);
        fromPanel.say("Yes I do, here you go!");
        fromPanel.updateHand();
        toPanel.updateHand();
    }

    @Override
    public void seriesCompleted(Player player, Series series) {
        PlayerPanel panel = gameBoard.getPlayerPanel(player);
        panel.updateComplete();
    }

    @Override
    public void goFish(Player playerAsking, Player playerAsked) {
        setMessage(playerAsking.getName() + " goes fishing\n");
        PlayerPanel panel = gameBoard.getPlayerPanel(playerAsked);
        panel.say("Go fish!");
    }

    @Override
    public void error(String message) {
        setErrorMessage(message);
    }
    
    private void initGameBoard() {
        gameBoard.clear();
        boolean forceShowOfSeries = loadedConfig.getForceShowOfSeries();
        for (Player player : loadedConfig.getPlayers()) {
            PlayerPanel panel = gameBoard.addPlayer(player);
            panel.showCompletedSeries(forceShowOfSeries || player.isHuman());
            panel.updatePanels();
        }
    }
    
    private void showInfoDialog(String message) {
        String title = "GoFish";
        int type = JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(this, message, title, type);
    }

}
