package gofish.swing.config;

import gofish.Game;
import gofish.model.Player.Type;
import gofish.swing.config.manual.Player;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

public class ManualCard extends ConfigCard {
    
    public ManualCard(ActionListener listener) {
        super(listener);
    }
    
    @Override
    protected void initComponents() {
        for (int i = 0; i < Game.MAX_NUM_PLAYERS; i++) {
            Type type = (i == 0) ? Type.HUMAN : Type.COMPUTER;
            String name = "Player " + (i + 1);
            Player player = new Player(type, name);
            if (i < Game.MIN_NUM_PLAYERS) {
                // Lock first 3 players
                player.lock();
            } else {
                // Disable other players by default
                player.setEnabled(false);
            }
            center.add(player);
        }
        
        JCheckBox allowMultipleRequests = new JCheckBox("Allow multiple requests");
        allowMultipleRequests.setSelected(true);
        center.add(allowMultipleRequests);
        
        JCheckBox forceShowOfSeries = new JCheckBox("Force show completed series");
        forceShowOfSeries.setSelected(true);
        center.add(forceShowOfSeries);
    }

}
