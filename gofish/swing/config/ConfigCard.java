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
    
    final private static String ERROR_ICON = "exclamation.png";
    
    protected JPanel center;
    
    protected JLabel errorLabel;
    
    private JButton startButton;
    
    private SwingGame game;
    
    private ConfigDialog dialog;
    
    public ConfigCard(SwingGame game, ConfigDialog dialog) {
        this.game = game;
        this.dialog = dialog;
        initUI();        
    }
    
    public SwingGame getGame() {
        return game;
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        center = new JPanel();
        add(center, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        add(buttonsPanel, BorderLayout.PAGE_END);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.showCard(ConfigDialog.MAIN);
            }
        });
        buttonsPanel.add(backButton);
        
        Icon errorIcon = SwingUtils.getIcon(ERROR_ICON);
        errorLabel = new JLabel(errorIcon);
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
        buttonsPanel.add(startButton);
        
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
