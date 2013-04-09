package gofish.swing;

import gofish.ConfigFactory;
import gofish.swing.config.MainCard;
import gofish.swing.config.ManualCard;
import gofish.swing.config.XMLCard;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConfigDialog extends JDialog implements ActionListener, ConfigFactory {
    
    final public static String MAIN = "main";
    
    final public static String MANUAL = "manual";
    
    final public static String XML = "xml";
    
    private JPanel panel;

    public ConfigDialog(JFrame parent) {
        super(parent, "GoFish - Game Configuration", true);
        
        panel = new JPanel(new CardLayout());
        panel.add(new MainCard(this), MAIN);
        panel.add(new ManualCard(this), MANUAL);
        panel.add(new XMLCard(this), XML);
        add(panel);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        CardLayout layout = (CardLayout) panel.getLayout();
        layout.show(panel, button.getName());
    }

    @Override
    public gofish.Config getConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
