package gofish.swing;

import gofish.exception.GameStoppedException;
import gofish.model.Player;
import gofish.swing.player.PlayerPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
    
    final private static int NUM_ROWS = 2;
    
    final private static int NUM_COLS = 3;
    
    final private static int GAP = 10;
    
    private Map<Player, PlayerPanel> playerPanels = new HashMap<>();
    
    private MouseListener listener = new LastClickedListener();
    
    private PlayerPanel current;
    
    private PlayerPanel lastClicked;

    public GameBoardPanel() {
        setLayout(new GridLayout(NUM_ROWS, NUM_COLS, GAP, GAP));
    }
    
    public void clear() {
        playerPanels.clear();
        removeAll();
        repaint();
    }
    
    public PlayerPanel addPlayer(Player player) {
        PlayerPanel panel = new PlayerPanel(player);
        panel.addMouseListener(listener);
        playerPanels.put(player, panel);
        add(panel);
        return panel;
    }
    
    public PlayerPanel getPlayerPanel(Player player) {
        return playerPanels.get(player);
    }
    
    public void setCurrentPlayer(Player player) {
        if (current != null) {
            current.isNotPlaying();
        }
        PlayerPanel panel = playerPanels.get(player);
        panel.isPlaying();
        current = panel;
    }
    
    synchronized public PlayerPanel getLastClicked() {
        if (lastClicked == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new GameStoppedException(e);
            }
        }
        PlayerPanel temp = lastClicked;
        lastClicked = null;
        return temp;
    }
    
    synchronized private void setLastClicked(PlayerPanel player) {
        lastClicked = player;
        this.notify();
    }

    private class LastClickedListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            setLastClicked((PlayerPanel) e.getSource());
        }
    }

}
