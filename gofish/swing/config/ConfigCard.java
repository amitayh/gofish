package gofish.swing.config;

import gofish.config.ConfigFactory;
import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

abstract public class ConfigCard extends JPanel implements ConfigFactory {
    
    protected JPanel center;
    
    protected JPanel bottom;
    
    protected JButton startButton;
    
    protected SwingGame game;
    
    private JLabel errorLabel;
    
    private ConfigDialog dialog;
    
    public ConfigCard(SwingGame game, ConfigDialog dialog) {
        super(new BorderLayout());
        this.game = game;
        this.dialog = dialog;
        initUI();        
    }

    private void initUI() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        errorLabel = new JLabel();
        add(errorLabel, BorderLayout.PAGE_START);
        
        center = new JPanel(new GridBagLayout());
        add(center, BorderLayout.CENTER);
        
        LayoutManager layout = new FlowLayout(FlowLayout.RIGHT);
        bottom = new JPanel(layout);
        add(bottom, BorderLayout.PAGE_END);
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.showCard(ConfigDialog.MAIN);
            }
        });
        bottom.add(backButton);
        
        startButton = new JButton("Start game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                game.start(getConfig());
            }
        });
        bottom.add(startButton);
        
        initComponents();
    }
    
    protected void setError(String text) {
        errorLabel.setText(SwingUtils.error(text));
        startButton.setEnabled(false);
    }
    
    protected void clearError() {
        errorLabel.setText("");
        startButton.setEnabled(true);
    }
    
    abstract protected void initComponents();

}
