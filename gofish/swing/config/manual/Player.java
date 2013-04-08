package gofish.swing.config.manual;

import gofish.model.Player.Type;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Player extends JPanel implements ActionListener {
    
    final private static Type[] TYPES = {Type.HUMAN, Type.COMPUTER};
    
    final private static int COLUMNS = 20;
    
    private JCheckBox state;
    
    private JComboBox type;
    
    private JTextField name;
    
    public Player(Type type, String name) {
        init();
        this.type.setSelectedItem(type);
        this.name.setText(name);
    }
    
    public void lock() {
        state.setEnabled(false);
    }
    
    public gofish.model.Player createPlayer() {
        String playerName = name.getText();
        gofish.model.Player player;
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
        state.addActionListener(this);
        add(state);
        
        type = new JComboBox(TYPES);
        add(type);
        
        name = new JTextField(COLUMNS);
        add(name);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.setEnabled(state.isSelected());
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        state.setSelected(enabled);
        type.setEnabled(enabled);
        name.setEnabled(enabled);
    }

}
