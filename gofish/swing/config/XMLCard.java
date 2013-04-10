package gofish.swing.config;

import gofish.Config;
import gofish.swing.ConfigDialog;
import gofish.swing.SwingGame;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class XMLCard extends ConfigCard {

    public XMLCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
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

    @Override
    public Config getConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
