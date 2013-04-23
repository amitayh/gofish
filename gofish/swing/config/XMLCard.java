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

    public XMLCard(SwingGame game, ConfigDialog dialog) {
        super(game, dialog);
    }
    
    @Override
    public void initComponents() {
        center.setBorder(new TitledBorder("XML Configuration"));
        
        filenameField = new JTextField(COLUMNS);
        filenameField.setEditable(false);
        center.add(filenameField);
        
        chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("XML files", "xml");
        chooser.removeChoosableFileFilter(chooser.getFileFilter());
        chooser.addChoosableFileFilter(filter);
        
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
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (selected.isFile()) {
                chooseFile(selected);
            }
        }
    }

    private void chooseFile(File file) {
        filenameField.setText(file.getAbsolutePath());
        XMLConfigFactory factory = new Factory();
        
        try {
            factory.validate(file);
            config = factory.getConfig();
            startButton.setEnabled(true);
        } catch (ConfigValidationException e) {
            startButton.setEnabled(false);
        }
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
                    player = new Human(name);
                    break;
                default:
                    throw new RuntimeException("Unexpected player type");
            }
            return player;
        }
        
    }

}
