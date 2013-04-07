package gofish.swing.config;

import gofish.swing.Config;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainCard extends JPanel {
    
    public MainCard(ActionListener listener) {
        JButton manual = new JButton("Manual configuration");
        manual.setName(Config.MANUAL);
        manual.addActionListener(listener);
        add(manual);
        
        JButton xml = new JButton("XML configuration");
        xml.setName(Config.XML);
        xml.addActionListener(listener);
        add(xml);
    }

}
