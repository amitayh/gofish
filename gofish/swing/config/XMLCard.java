package gofish.swing.config;

import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class XMLCard extends ConfigCard {
    
    public XMLCard(ActionListener listener) {
        super(listener);
    }
    
    @Override
    public void initComponents() {
        center.setBorder(new TitledBorder("XML Configuration"));
        
        JFileChooser file = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("XML files", "xml");
        file.removeChoosableFileFilter(file.getFileFilter());
        file.addChoosableFileFilter(filter);
        center.add(file);
    }

}
