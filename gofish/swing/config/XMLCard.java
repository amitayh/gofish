package gofish.swing.config;

import gofish.config.Config;
import gofish.config.XMLConfigFactory;
import gofish.exception.ConfigValidationException;
import gofish.model.Player;
import gofish.swing.ConfigDialog;
import gofish.swing.SwingGame;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class XMLCard extends ConfigCard {
    
    final private static int COLUMNS = 20;
    
    private JTextField filenameField;
    
    private JFileChooser chooser;
    
    private Config config;
    
    private XMLConfigFactory factory;

    public XMLCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    public void initComponents() {
        center.setBorder(new TitledBorder("XML Configuration"));
        
        filenameField = new JTextField(COLUMNS);
        filenameField.setEditable(false);
        center.add(filenameField);
        
        JButton chooseFileButton = new JButton("Choose file");
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser();
            }
        });
        center.add(chooseFileButton);
        
        // Disable start button by default
        startButton.setEnabled(false);
    }

    @Override
    public Config getConfig() {
        return config;
    }
    
    private void openFileChooser() {
        JFileChooser fc = getFileChooser();
        int option = fc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = fc.getSelectedFile();
            if (selected.isFile()) {
                chooseFile(selected);
            }
        }
    }

    private void chooseFile(File file) {
        filenameField.setText(file.getAbsolutePath());
        XMLConfigFactory cf = getConfigFactory();
        try {
            cf.validate(file);
            config = cf.getConfig();
            clearError();
        } catch (ConfigValidationException e) {
            setError(e.getMessage());
        }
    }

    private JFileChooser getFileChooser() {
        if (chooser == null) {
            chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("XML files", "xml");
            chooser.removeChoosableFileFilter(chooser.getFileFilter());
            chooser.addChoosableFileFilter(filter);
        }
        return chooser;
    }

    private XMLConfigFactory getConfigFactory() {
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
    }
    
    private static class Factory extends XMLConfigFactory {

        @Override
        protected Player createPlayer(Player.Type type, String name) {
            Player player;
            switch (type) {
                case COMPUTER: 
                    player = new Computer(name);
                    break;
                case HUMAN:
                    player = new Human(name);
                    break;
                default:
                    throw new RuntimeException("Unexpected player type");
            }
            return player;
        }
        
    }

}
