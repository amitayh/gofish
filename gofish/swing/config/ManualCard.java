package gofish.swing.config;

import gofish.Game;
import gofish.config.Config;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player.Type;
import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import gofish.swing.config.manual.PlayerSettingsPanel;
import gofish.swing.player.AbstractPlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class ManualCard extends ConfigCard {
    
    private JPanel playersPanel;
    
    private JButton addButton;
    
    private JCheckBox allowMultipleRequests;
    
    private JCheckBox forceShowOfSeries;
    
    private PropertyChangeListener listener;
    
    private List<PlayerSettingsPanel> playersSettings;
    
    public ManualCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    protected void initComponents() {
        center.setLayout(new MigLayout("", "[][grow]", "[][][grow][][]"));
        center.setBorder(new TitledBorder("Manual Configuration"));
        
        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        center.add(playersPanel, "cell 0 0 2 1,growx");
        
        Icon addIcon = SwingUtils.getIcon("add.png");
        addButton = new JButton("Add New Player", addIcon);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer(Type.COMPUTER);
            }
        });
        center.add(addButton, "cell 0 1");
        
        center.add(errorLabel, "cell 1 1");
        
        allowMultipleRequests = new JCheckBox("Allow multiple requests");
        allowMultipleRequests.setSelected(true);
        center.add(allowMultipleRequests, "cell 0 3 2 1");
        
        forceShowOfSeries = new JCheckBox("Force show completed series");
        center.add(forceShowOfSeries, "cell 0 4 2 1");
        
        listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                validatePlayerNames();
            }
        };
        playersSettings = new LinkedList<>();
        addDefaultPlayers();
    }
    
    public PlayerSettingsPanel addPlayer(Type type) {
        return addPlayer(type, "");
    }
    
    public PlayerSettingsPanel addPlayer(Type type, String name) {
        // Create player settings panel
        PlayerSettingsPanel player = new PlayerSettingsPanel(this, type, name);
        player.addPropertyChangeListener(PlayerSettingsPanel.NAME_CHANGE_EVENT, listener);
        
        // Add to players panel
        playersSettings.add(player);
        playersPanel.add(player);
        playersPanel.revalidate();
        
        checkNumberOfPlayers();
        validatePlayerNames();
        
        return player;
    }
    
    public void removePlayer(PlayerSettingsPanel player) {
        // Remove from players panel
        playersSettings.remove(player);
        playersPanel.remove(player);
        playersPanel.revalidate();
        
        checkNumberOfPlayers();
        validatePlayerNames();
    }

    @Override
    public Config getConfig() {
        Config config = new Config();
        
        for (PlayerSettingsPanel playerSettings : playersSettings) {
            AbstractPlayer player = playerSettings.createPlayer();
            player.setGame(game);
            config.addPlayer(player);
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

    private void addDefaultPlayers() {
        for (int i = 1; i <= Game.MIN_NUM_PLAYERS; i++) {
            Type type = (i == 1) ? Type.HUMAN : Type.COMPUTER;
            String name = "Player " + i;
            PlayerSettingsPanel player = addPlayer(type, name);
            player.disableRemove();
        }
    }

    private void checkNumberOfPlayers() {
        boolean canAdd = playersSettings.size() < Game.MAX_NUM_PLAYERS;
        addButton.setEnabled(canAdd);
    }
    
    private void validatePlayerNames() {
        boolean isValid = true;

        Set<String> names = new HashSet<>();
        for (PlayerSettingsPanel playerSettings : playersSettings) {
            String name = playerSettings.getPlayerName();
            if (name.isEmpty() || names.contains(name)) {
                isValid = false;
                break;
            } else {
                names.add(name);
            }
        }

        if (!isValid) {
            setError("Empty or duplicate player name");
        } else {
            clearError();
        }
    }

}
