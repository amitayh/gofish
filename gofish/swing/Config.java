package gofish.swing;

import gofish.ConfigFactory;
import gofish.swing.config.MainCard;
import gofish.swing.config.ManualCard;
import gofish.swing.config.XMLCard;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Config extends JPanel implements ActionListener, ConfigFactory {
    
    final public static String MAIN = "main";
    
    final public static String MANUAL = "manual";
    
    final public static String XML = "xml";

    public Config() {
        super(new CardLayout());
        
        add(new MainCard(this), MAIN);
        add(new ManualCard(this), MANUAL);
        add(new XMLCard(this), XML);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        CardLayout layout = (CardLayout) getLayout();
        layout.show(this, button.getName());
    }

    @Override
    public gofish.Config getConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
