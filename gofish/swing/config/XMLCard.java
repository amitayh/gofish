package gofish.swing.config;

import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class XMLCard extends ConfigCard {
    
    public XMLCard(ActionListener listener) {
        super(listener);
    }
    
    @Override
    public void initComponents() {
        JFileChooser file = new JFileChooser();
        center.add(file);
    }

}
