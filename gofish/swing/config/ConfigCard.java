package gofish.swing.config;

import gofish.ConfigFactory;
import gofish.swing.ConfigDialog;
import gofish.swing.SwingGame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

abstract public class ConfigCard extends JPanel implements ConfigFactory {
    
    protected JPanel center;
    
    protected JPanel bottom;
    
    protected JButton startButton;
    
    protected SwingGame game;
    
    private ConfigDialog dialog;
    
    public ConfigCard(SwingGame game, ConfigDialog dialog) {
        super(new BorderLayout());
        this.game = game;
        this.dialog = dialog;
        initUI();        
    }

    private void initUI() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
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
    
    abstract protected void initComponents();

}
