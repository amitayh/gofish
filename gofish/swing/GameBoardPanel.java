package gofish.swing;

import gofish.model.Player;
import gofish.swing.player.AbstractPlayer;
import gofish.swing.player.PlayerPanel;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
    
    final private static int NUM_ROWS = 2;
    
    final private static int NUM_COLS = 3;
    
    final private static int GAP = 10;
    
    private Map<Player, PlayerPanel> playerPanels = new HashMap<>();
    
    private PlayerPanel current;

    public GameBoardPanel() {
        setLayout(new GridLayout(NUM_ROWS, NUM_COLS, GAP, GAP));
    }
    
    public void clear() {
        playerPanels.clear();
        removeAll();
    }
    
    public PlayerPanel addPlayer(Player player) {
        ((AbstractPlayer) player).setGameBoard(this);
        PlayerPanel panel = new PlayerPanel(player);        
        playerPanels.put(player, panel);
        add(panel);
        
        return panel;
    }
    
    public void setCurrentPlayer(Player player) {
        if (current != null) {
            current.isNotPlaying();
        }
        PlayerPanel panel = playerPanels.get(player);
        panel.isPlaying();
        current = panel;
    }

    public Collection<PlayerPanel> getPlayers() {
        return playerPanels.values();
    }

}
