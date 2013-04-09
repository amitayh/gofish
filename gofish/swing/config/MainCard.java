package gofish.swing.config;

import gofish.swing.ConfigDialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainCard extends JPanel {
    
    public MainCard(ActionListener listener) {
        super(new GridBagLayout());
        
        JButton manual = new JButton("Manual Configuration");
        manual.setName(ConfigDialog.MANUAL);
        manual.addActionListener(listener);
        add(manual);
        
        JButton xml = new JButton("XML Configuration");
        xml.setName(ConfigDialog.XML);
        xml.addActionListener(listener);
        add(xml);
    }

}
