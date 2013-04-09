package gofish.swing;

import gofish.ConfigFactory;
import gofish.swing.config.MainCard;
import gofish.swing.config.ManualCard;
import gofish.swing.config.XMLCard;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ConfigDialog extends JDialog implements ActionListener, ConfigFactory {
    
    final public static String MAIN = "main";
    
    final public static String MANUAL = "manual";
    
    final public static String XML = "xml";

    public ConfigDialog(JFrame parent) {
        super(parent, "GoFish - Game Configuration", true);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());
        contentPane.add(new MainCard(this), MAIN);
        contentPane.add(new ManualCard(this), MANUAL);
        contentPane.add(new XMLCard(this), XML);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Container contentPane = getContentPane();
        CardLayout layout = (CardLayout) contentPane.getLayout();
        JButton button = (JButton) e.getSource();
        layout.show(contentPane, button.getName());
    }

    @Override
    public gofish.Config getConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
