package gofish.swing.config;

import gofish.config.ConfigFactory;
import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

abstract public class ConfigCard extends JPanel implements ConfigFactory {
    
    final private static Icon ERROR_ICON = SwingUtils.getIcon("exclamation.png");
    
    protected SwingGame game;
    
    protected JPanel center;
    
    protected JLabel errorLabel;
    
    private JButton startButton;
    
    private ConfigDialog dialog;
    
    public ConfigCard(SwingGame game, ConfigDialog dialog) {
        this.game = game;
        this.dialog = dialog;
        initUI();        
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        center = new JPanel();
        add(center, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonsPanel, BorderLayout.PAGE_END);
        
        JPanel buttonsBar = new JPanel();
        buttonsBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(buttonsBar, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.showCard(ConfigDialog.MAIN);
            }
        });
        buttonsBar.add(backButton);
        
        errorLabel = new JLabel(ERROR_ICON);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        
        startButton = new JButton("Start game");
        startButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                game.start(getConfig());
            }
        });
        buttonsBar.add(startButton);
        
        initComponents();
    }
    
    protected void setError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        startButton.setEnabled(false);
    }
    
    protected void clearError() {
        errorLabel.setVisible(false);
        startButton.setEnabled(true);
    }
    
    abstract protected void initComponents();

}
