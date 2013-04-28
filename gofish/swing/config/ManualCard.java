package gofish.swing.config;

import gofish.config.Config;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player.Type;
import gofish.swing.SwingGame;
import gofish.swing.config.manual.PlayerSettingsPanel;
import gofish.swing.player.AbstractPlayer;
import java.awt.GridBagConstraints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

public class ManualCard extends ConfigCard {
    
    private JCheckBox allowMultipleRequests;
    
    private JCheckBox forceShowOfSeries;
    
    private List<PlayerSettingsPanel> playersSettings;
    
    public ManualCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    protected void initComponents() {
        center.setBorder(new TitledBorder("Manual Configuration"));
        PropertyChangeListener listener = new NameChangeListener();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        
        playersSettings = new LinkedList<>();
        for (int i = 0; i < Game.MAX_NUM_PLAYERS; i++) {
            Type type = (i == 0) ? Type.HUMAN : Type.COMPUTER;
            String name = "Player " + (i + 1);
            PlayerSettingsPanel player = new PlayerSettingsPanel(type, name);
            player.addPropertyChangeListener(PlayerSettingsPanel.NAME_CHANGE_EVENT, listener);
            if (i < Game.MIN_NUM_PLAYERS) {
                // Lock first 3 players (minimum required)
                player.lock();
            } else {
                // Disable other players by default
                player.setEnabled(false);
            }
            constraints.gridy = i;
            center.add(player, constraints);
            playersSettings.add(player);
        }
        
        constraints.gridy++;
        allowMultipleRequests = new JCheckBox("Allow multiple requests");
        allowMultipleRequests.setSelected(true);
        center.add(allowMultipleRequests, constraints);
        
        constraints.gridy++;
        forceShowOfSeries = new JCheckBox("Force show completed series");
        center.add(forceShowOfSeries, constraints);
    }

    @Override
    public Config getConfig() {
        Config config = new Config();
        
        for (PlayerSettingsPanel playerSettings : playersSettings) {
            if (playerSettings.isEnabled()) {
                AbstractPlayer player = playerSettings.createPlayer();
                player.setGame(game);
                config.addPlayer(player);
            }
        }
        dealCards(config.getPlayers());
        config.setAllowMutipleRequests(allowMultipleRequests.isSelected());
        config.setForceShowOfSeries(forceShowOfSeries.isSelected());
        
        return config;
    }
    
    private void dealCards(List<gofish.model.Player> players) {
        Deck deck = new Deck();
        deck.shuffle();
        
        int index = 0;
        while (deck.size() > 0) {
            gofish.model.Player player = players.get(index);
            Card card = deck.deal();
            player.addCard(card);
            index = (index + 1) % players.size();
        }
    }
    
    private class NameChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            boolean isValid = true;
            
            Set<String> names = new HashSet<>();
            for (PlayerSettingsPanel playerSettings : playersSettings) {
                String name = playerSettings.getName();
                if (name.isEmpty() || names.contains(name)) {
                    isValid = false;
                    break;
                } else {
                    names.add(name);
                }
            }
            
            if (!isValid) {
                setError("Invalid configuration");
            } else {
                clearError();
            }
        }
        
    }

}
