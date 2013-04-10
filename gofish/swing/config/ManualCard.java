package gofish.swing.config;

import gofish.Config;
import gofish.Game;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player.Type;
import gofish.swing.ConfigDialog;
import gofish.swing.SwingGame;
import gofish.swing.config.manual.Player;
import java.awt.GridBagConstraints;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;

public class ManualCard extends ConfigCard {
    
    private JCheckBox allowMultipleRequests;
    
    private JCheckBox forceShowOfSeries;
    
    private List<Player> players;
    
    public ManualCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    protected void initComponents() {
        center.setBorder(new TitledBorder("Manual Configuration"));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        
        players = new LinkedList<>();
        for (int i = 0; i < Game.MAX_NUM_PLAYERS; i++) {
            Type type = (i == 0) ? Type.HUMAN : Type.COMPUTER;
            String name = "Player " + (i + 1);
            Player player = new Player(type, name);
            if (i < Game.MIN_NUM_PLAYERS) {
                // Lock first 3 players (minimum required)
                player.lock();
            } else {
                // Disable other players by default
                player.setEnabled(false);
            }
            constraints.gridy = i;
            center.add(player, constraints);
            players.add(player);
        }
        
        constraints.gridy++;
        allowMultipleRequests = new JCheckBox("Allow multiple requests");
        allowMultipleRequests.setSelected(true);
        center.add(allowMultipleRequests, constraints);
        
        constraints.gridy++;
        forceShowOfSeries = new JCheckBox("Force show completed series");
        forceShowOfSeries.setSelected(true);
        center.add(forceShowOfSeries, constraints);
    }

    @Override
    public Config getConfig() {
        Config config = new Config();
        
        for (Player player : players) {
            if (player.isEnabled()) {
                config.addPlayer(player.createPlayer());
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

}
