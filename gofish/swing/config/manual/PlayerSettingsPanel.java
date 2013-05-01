package gofish.swing.config.manual;

import gofish.model.Player.Type;
import gofish.swing.SwingUtils;
import gofish.swing.config.ManualCard;
import gofish.swing.player.AbstractPlayer;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;

public class PlayerSettingsPanel extends JPanel {
    
    final public static String NAME_CHANGE_EVENT = "name_change";
    
    final private static Type[] TYPES = {Type.HUMAN, Type.COMPUTER};
    
    final private static Icon REMOVE_ICON = SwingUtils.getIcon("delete.png");
    
    private ManualCard parent;
    
    private JComboBox type;
    
    private JTextField name;
    
    private JButton removeButton;
    
    public PlayerSettingsPanel(ManualCard parent, Type type, String name) {
        this.parent = parent;
        initComponents();
        this.type.setSelectedItem(type);
        this.name.setText(name);
    }
    
    public String getPlayerName() {
        return name.getText();
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

    private void initComponents() {
        setLayout(new MigLayout("", "0[][grow][]0", "0[]"));
        
        type = new JComboBox(TYPES);
        add(type, "cell 0 0,growx");
        
        name = new JTextField();
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fireNameChangeEvent();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                fireNameChangeEvent();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                fireNameChangeEvent();
            }
        });
        add(name, "flowx,cell 1 0,growx");
        
        removeButton = new JButton("Remove", REMOVE_ICON);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlayer();
            }
        });
        add(removeButton, "cell 2 0");
    }
    
    private void removePlayer() {
        parent.removePlayer(this);
    }
    
    private void fireNameChangeEvent() {
        firePropertyChange(NAME_CHANGE_EVENT, false, true);
    }

    public void disableRemove() {
        removeButton.setEnabled(false);
    }

}
