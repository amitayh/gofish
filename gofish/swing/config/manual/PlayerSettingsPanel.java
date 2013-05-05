package gofish.swing.config.manual;

import gofish.model.Player;
import gofish.model.Player.Type;
import gofish.model.player.Computer;
import gofish.swing.SwingUtils;
import gofish.swing.config.ManualCard;
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
    
    final public static String NAME_CHANGE_EVENT = "name-change";
    
    final private static Type[] TYPES = {Type.HUMAN, Type.COMPUTER};
    
    final private static Icon REMOVE_ICON = SwingUtils.getIcon("delete.png");
    
    private ManualCard parent;
    
    private JComboBox<Type> typeComboBox;
    
    private JTextField nameTextField;
    
    private JButton removeButton;
    
    private String name;
    
    public PlayerSettingsPanel(ManualCard parent, Type type, String name) {
        this.parent = parent;
        initComponents();
        typeComboBox.setSelectedItem(type);
        nameTextField.setText(name);
    }
    
    public void focusName() {
        nameTextField.requestFocus();
    }
    
    public void disableRemove() {
        removeButton.setEnabled(false);
    }
    
    public String getPlayerName() {
        return name;
    }
    
    public Player createPlayer() {
        Player player;
        if (typeComboBox.getSelectedItem() == Type.HUMAN) {
            player = new Human(parent.getGame(), name);
        } else {
            player = new Computer(name);
        }
        return player;
    }

    private void initComponents() {
        setLayout(new MigLayout("", "0[][grow][]0", "0[]"));
        
        typeComboBox = new JComboBox<>(TYPES);
        add(typeComboBox, "cell 0 0,growx");
        
        nameTextField = new JTextField();
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
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
        add(nameTextField, "flowx,cell 1 0,growx");
        
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
        String oldName = name;
        name = nameTextField.getText();
        firePropertyChange(NAME_CHANGE_EVENT, oldName, name);
    }

}
