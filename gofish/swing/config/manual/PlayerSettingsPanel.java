package gofish.swing.config.manual;

import gofish.model.Player.Type;
import gofish.swing.player.AbstractPlayer;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerSettingsPanel extends JPanel {
    
    final private static Type[] TYPES = {Type.HUMAN, Type.COMPUTER};
    
    final private static int COLUMNS = 20;
    
    private JCheckBox state;
    
    private JComboBox type;
    
    private JTextField name;
    
    public PlayerSettingsPanel(Type type, String name) {
        init();
        this.type.setSelectedItem(type);
        this.name.setText(name);
    }
    
    public void lock() {
        state.setEnabled(false);
    }
    
    public AbstractPlayer createPlayer() {
        String playerName = name.getText();
        AbstractPlayer player;
        if (type.getSelectedItem() == Type.HUMAN) {
            player = new Human(playerName);
        } else {
            player = new Computer(playerName);
        }
        return player;
    }

    private void init() {
        state = new JCheckBox();
        state.setSelected(true);
        state.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(state.isSelected());
            }
        });
        add(state);
        
        type = new JComboBox(TYPES);
        add(type);
        
        name = new JTextField(COLUMNS);
        add(name);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        state.setSelected(enabled);
        type.setEnabled(enabled);
        name.setEnabled(enabled);
    }

}
