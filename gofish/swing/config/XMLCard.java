package gofish.swing.config;

import gofish.config.Config;
import gofish.config.XMLConfigFactory;
import gofish.exception.ConfigValidationException;
import gofish.model.Player;
import gofish.model.player.Computer;
import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import gofish.swing.player.Human;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.MigLayout;

public class XMLCard extends ConfigCard {
    
    final private static String BROWSE_ICON = "folder.png";
    
    private JTextField filenameField;
    
    private JFileChooser chooser;
    
    private Config config;
    
    private XMLConfigFactory factory;

    public XMLCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    public void initComponents() {
        center.setLayout(new MigLayout("", "[][grow][]", "[][]"));
        center.setBorder(new TitledBorder("XML Configuration"));
        
        JLabel fileLabel = new JLabel("File location:");
        center.add(fileLabel, "cell 0 0");
        
        filenameField = new JTextField();
        filenameField.setEditable(false);
        center.add(filenameField, "cell 1 0,growx");
        
        Icon browseIcon = SwingUtils.getIcon(BROWSE_ICON);
        JButton chooseFileButton = new JButton("Browse...", browseIcon);
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser();
            }
        });
        center.add(chooseFileButton, "cell 2 0");
        
        center.add(errorLabel, "cell 1 1 2 1");
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
    
    private class Factory extends XMLConfigFactory {

        @Override
        protected Player createPlayer(Player.Type type, String name) {
            Player player;
            switch (type) {
                case COMPUTER: 
                    player = new Computer(name);
                    break;
                case HUMAN:
                    player = new Human(getGame(), name);
                    break;
                default:
                    throw new RuntimeException("Unexpected player type");
            }
            return player;
        }
        
    }

}
